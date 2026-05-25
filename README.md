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

## Known limitations

| Area              | Current behaviour                                             | Improvement                                                                                   |
|-------------------|---------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| Authentication    | No login — the app runs entirely as a guest                   | Allow users to sign in with a TMDB account to sync their watchlist across devices             |
| Watchlist storage | Saved locally on the device using Room                        | Sync the watchlist with TMDB's account favorites so it persists across reinstalls and devices |
| Movie ordering    | Movies are displayed in the default order returned by the API | Add sorting options (by rating, release date, popularity) and search                          |
