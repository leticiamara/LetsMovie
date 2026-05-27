# LetsMovie

An Android app for browsing popular movies, discovering new releases, and keeping a personal watchlist — powered by the [TMDB API](https://www.themoviedb.org/documentation/api).

## Features

- Browse movies by category: **Popular**, **Now Playing**, and **Upcoming**
- View movie details: overview, genres, rating, release date, original title and language
- Add and remove movies from a personal **Watchlist**
- Brazilian Portuguese (pt-BR) localisation

## Authentication

The app uses TMDB **guest sessions** — no account or login is required. On first launch a guest session is created automatically and stored locally, so the app is ready to use immediately.

## How to run locally

### Prerequisites

- Android Studio Hedgehog or later
- A TMDB API key ([get one here](https://www.themoviedb.org/settings/api))

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/leticiamara/LetsMovie.git
   cd LetsMovie
   ```

2. Add your TMDB API key to `local.properties` (create the file if it does not exist):
   ```properties
   tmdb.api.key=YOUR_API_KEY_HERE
   ```

3. Open the project in Android Studio and let Gradle sync.

4. Run on a device or emulator (**min SDK 23**, target SDK 36).

## Running the tests

### Unit tests

Run all unit tests (use cases, ViewModels, repositories) with:

```bash
./gradlew testDebugUnitTest
```

### Instrumented tests (Compose UI)

Instrumented tests require a connected device or running emulator:

```bash
./gradlew connectedDebugAndroidTest
```

Tests use fake repositories instead of real network or database calls, so no API key or network connection is needed.

## Architecture

The app follows the [official Android architecture guidelines](https://developer.android.com/topic/architecture) with three layers. Dependencies flow in one direction: **UI → Domain → Data**.

```
app/src/main/java/com/leticiafernandes/letsmovie/
├── ui/      ← Screens, ViewModels, UiState classes, UI models
├── domain/  ← Use cases, domain models, mappers
└── data/    ← Repositories, remote (Retrofit/DTOs) and local (Room/entities)
```

### UI layer

Each screen has a sealed `UiState` class. ViewModels expose state via `StateFlow` or `LiveData` and are the only entry point for user events — screens never call repositories or use cases directly.

### Domain layer

One class per use case with a single `invoke` function. Use cases coordinate repositories and return domain models, keeping ViewModels thin and business logic testable without Android dependencies.

### Data layer

Repositories are the single source of truth. Network calls are wrapped in `NetworkResult<T>` (Success / HttpError / NetworkError) so error handling is typed end-to-end. Remote data uses **Retrofit + kotlinx.serialization**; local data uses **Room**. An OkHttp interceptor injects the API key and device locale into every request.

### Key libraries

| Concern | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Navigation | Jetpack Navigation Component |
| DI | Hilt |
| Async | Kotlin Coroutines + Flow |
| Paging | Paging 3 |
| Networking | Retrofit + OkHttp |
| Serialization | kotlinx.serialization |
| Local storage | Room |
| Image loading | Coil |

## Known limitations

| Area              | Current behaviour                                             | Improvement                                                                                   |
|-------------------|---------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| Authentication    | No login — the app runs entirely as a guest                   | Allow users to sign in with a TMDB account to sync their watchlist across devices             |
| Watchlist storage | Saved locally on the device using Room                        | Sync the watchlist with TMDB's account favorites so it persists across reinstalls and devices |
| Movie ordering    | Movies are displayed in the default order returned by the API | Add sorting options (by rating, release date, popularity) and search                          |
