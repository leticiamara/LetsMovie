# LetsMovie – Architecture Guide

Based on the [official Android architecture guidelines](https://developer.android.com/topic/architecture).

---

## Layered Architecture

The app follows a three-layer architecture. Each layer has a single direction of dependency: UI → Domain → Data.

```
app/src/main/java/com/leticiafernandes/letsmovie/
├── ui/          ← UI layer   (screens, ViewModels, UI state, UI models)
├── domain/      ← Domain layer (use cases, domain models, mappers)
└── data/        ← Data layer  (repositories, data sources, DTOs, entities)
```

---

## UI Layer

**Official reference:** https://developer.android.com/topic/architecture/ui-layer

The UI layer renders state and forwards user events. It never contains business logic.

### Rules
- Every screen has a corresponding `*UiState` sealed class or data class.
- ViewModels expose state via `StateFlow<UiState>` (preferred for new screens) or `LiveData<UiState>`.
- Screens observe state and call ViewModel functions for events — they never call repositories or use cases directly.
- UI models (e.g. `MovieItem`) are separate from domain models (`Movie`). Mapping happens in the ViewModel or a dedicated mapper in `ui/`.

### Unidirectional Data Flow (UDF)
```
User event → ViewModel → Use Case → Repository
                 ↓
            UiState update → Screen re-renders
```

### UiState pattern
```kotlin
// Sealed class for distinct states
sealed class MoviesUiState {
    object Loading : MoviesUiState()
    data class Success(val movies: List<MovieItem>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

// ViewModel exposes it
val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()
```

### ViewModel rules
- Inject only **use cases** (never repositories directly).
- Use `viewModelScope` for all coroutines — cancellation is automatic.
- Do not hold references to `Context`, `Activity`, or any Android view.
- Survive configuration changes by design; do not re-fetch on rotation.

---

## Domain Layer

**Official reference:** https://developer.android.com/topic/architecture/domain-layer

The domain layer is optional but used here. It centralises business logic and keeps ViewModels thin.

```
domain/
├── model/       ← Pure Kotlin data classes (no Android/framework imports)
├── usecase/     ← One class per use case
└── mapper/      ← Domain model ↔ UI model conversions
```

### Use case rules
- One public function per class, named as a verb: `GetMoviesUseCase`, `ToggleFavoriteUseCase`.
- Operator `invoke` is the standard entry point for single-function use cases.
- Use cases can call multiple repositories and coordinate their results.
- They return domain models, never DTOs or UI models.
- They are `suspend fun` or return `Flow`.

```kotlin
class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository,
    private val isFavoriteUseCase: IsFavoriteUseCase
) {
    suspend operator fun invoke(movieId: Long): ToggleResult { ... }
}
```

### Domain model rules
- Plain Kotlin data classes — no `@Entity`, `@Serializable`, or other framework annotations.
- Nullability reflects real business semantics, not API quirks.

---

## Data Layer

**Official reference:** https://developer.android.com/topic/architecture/data-layer

The data layer is the single source of truth for all app data.

```
data/
├── repository/       ← Public API of the data layer (interfaces + impls)
├── remote/           ← Network data source (Retrofit service + impl)
│   ├── api/          ← Retrofit service interfaces
│   └── dto/          ← @Serializable network response models
├── local/            ← Room data source (DAOs, entities, converters)
│   ├── dao/          ← DAO interfaces
│   └── entity/       ← @Entity Room models
└── mapper/           ← DTO/Entity ↔ Domain model conversions
```

### Repository rules
- Repositories expose `suspend fun` or `Flow` — never raw Retrofit `Call` or RxJava types.
- They return domain models, never DTOs or entities.
- They wrap network calls in `safeApiCall` and return `NetworkResult<T>`.
- The repository interface lives in `domain/` or `data/repository/`; the implementation in `data/repository/`.

### NetworkResult — typed error handling
All network calls return `NetworkResult<T>`. Never catch generic `Exception` in ViewModels.

```kotlin
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class HttpError(val code: Int, val message: String?) : NetworkResult<Nothing>()
    data class NetworkError(val throwable: Throwable) : NetworkResult<Nothing>()
}
```

Usage in a repository:
```kotlin
override suspend fun listPopularMovies(page: Int): NetworkResult<MovieResult> =
    safeApiCall { moviesRemoteDataSource.listPopularMovies(page).mapToMovieResultDomain() }
```

Usage in a ViewModel:
```kotlin
when (val result = moviesUseCase.listPopularMovies()) {
    is NetworkResult.Success   -> _uiState.value = Success(result.data)
    is NetworkResult.HttpError -> _uiState.value = Error("Server error ${result.code}")
    is NetworkResult.NetworkError -> _uiState.value = Error("No connection")
}
```

### DTO rules
- Annotated with `@Serializable` (kotlinx.serialization).
- Use `@SerialName` to map snake_case API fields to camelCase Kotlin fields.
- All fields have sensible defaults so missing JSON keys never crash deserialization.
- DTOs never leave the data layer — map them to domain models before returning from a repository.

### Room entity rules
- Annotated with `@Entity` — separate class from the domain model.
- DAOs use `suspend fun` for one-shot queries and `Flow<T>` for observable queries.

---

## Networking

- **Retrofit** with `suspend` functions — no RxJava adapters.
- **kotlinx.serialization** — type-safe, Kotlin-null-aware, replaces Gson.
- **OkHttp interceptors** for cross-cutting concerns (API key, logging). Never add `@Query("api_key")` to individual service methods when an interceptor already handles it.
- Retrofit is configured in `RetrofitHelper` with `ignoreUnknownKeys = true` and `coerceInputValues = true` to be resilient to API changes.

---

## Dependency Injection

- **Hilt** throughout. No manual `object` singletons except `RetrofitHelper` (infrastructure).
- `@HiltViewModel` for all ViewModels.
- Modules live in `di/` and are scoped to `SingletonComponent` unless a narrower scope is justified.
- Bind interfaces to implementations with `@Binds`; provide third-party objects with `@Provides`.

---

## Asynchronous Work

- **Kotlin Coroutines** for all async operations.
- `viewModelScope` in ViewModels — automatically cancelled on `onCleared()`.
- `Dispatchers.IO` is not set manually in most cases; Retrofit and Room handle their own threading when called with `suspend`.
- `Flow` for streams (e.g. observing the favourites list from Room).
- `coroutineScope { async { } }` for parallel independent calls (e.g. fetching genres and movies simultaneously).

---

## Navigation

- **Jetpack Navigation Component** with a single `NavGraph`.
- Routes defined as a `sealed class Destination`.
- The `NavGraph` is the only place that wires screens to routes — screens do not know about routes.

---

## Testing

**Official reference:** https://developer.android.com/topic/architecture/recommendations#testing

| What | How | Location |
|---|---|---|
| Use cases | JUnit + MockK/Mockito | `test/` |
| ViewModels | JUnit + `TestCoroutineDispatcher` + `InstantTaskExecutorRule` | `test/` |
| Repositories | JUnit + MockWebServer for network; in-memory Room for local | `test/` |
| Compose screens | Compose UI test rules | `androidTest/` |

- Fake/stub repositories are preferred over mocking for ViewModel tests.
- Use cases must be testable without Android dependencies (no `Context`).

---

## What to avoid

| Avoid | Reason |
|---|---|
| Logic in Composables | Breaks testability and UDF |
| Calling repositories from ViewModels directly | Bypasses the domain layer |
| DTOs in domain or UI models | Leaks network representation into business logic |
| `GlobalScope` | Uncontrolled lifetime; use `viewModelScope` or `lifecycleScope` |
| `runBlocking` in production code | Blocks the thread; use `suspend` instead |
| Hardcoded API keys in source files | Use `BuildConfig` fields sourced from `local.properties` |
