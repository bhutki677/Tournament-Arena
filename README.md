# Tournament Arena

Native Android (Java) esports tournament app for Free Fire MAX players, built phase by phase.
This repository currently contains **phase 1A**: project skeleton, design system, animated
splash, auth host and the five destination main shell. All data is mock data behind
repository interfaces - there is no backend, no real authentication and no real money.

Package id: `com.vk.tournamentapp` · minSdk 24 · targetSdk/compileSdk 34 · Java 17 · AGP 8.5.2 · Gradle 8.7.

Build instructions: see [BUILD-NOTES.md](BUILD-NOTES.md).

## Navigation

| Host | Graph | Destination ids |
|---|---|---|
| `SplashActivity` (launcher) | - | routes to auth or main via `SessionManager.decideStartDestination()` |
| `AuthActivity` | `res/navigation/nav_auth.xml` | `nav_login`, `nav_register`, `nav_forgot_password`, `nav_otp` |
| `MainActivity` | `res/navigation/nav_main.xml` | `nav_home`, `nav_tournaments`, `nav_matches`, `nav_wallet`, `nav_profile` |
| `TournamentDetailsActivity` | - | launched with `AppConstants.ARG_TOURNAMENT_ID` |
| `NotificationsActivity` | - | standalone list host |

Bottom navigation item ids in `res/menu/bottom_nav_menu.xml` are identical to the graph
destination ids so `NavigationUI.setupWithNavController` needs no extra wiring.

## Design system

* `res/values/colors.xml` - `ta_color_*` brand/UI palette, `ta_status_*` workflow states
* `res/values/dimens.xml` - `ta_space_*`, `ta_radius_*`, `ta_text_*`, component heights
* `res/values/strings.xml` - every user facing string in the app
* `res/values/styles.xml` - `Widget.TA.*` widgets, `TextAppearance.TA.*` typography
* `res/values/themes.xml` - `Theme.TournamentArena` (+ `.Splash`, `.Auth`, `.Main`) and
  `ThemeOverlay.TA.*`
* `res/color/selector_*.xml` - nav tint, input stroke/hint, button text, chips

Hard rule: no colour, dimension or user facing string literal in a layout or in Java.

## Repository / service seams

`repositories/` holds interfaces plus `RepositoryCallback<T>`; `data/` holds the phase 1A
mock implementations that return empty lists after a short delay so every screen shows its
real loading and empty states. `data/RepositoryProvider` is the only place that decides
which implementation is used. `services/SessionManager` stores a mock session;
`services/IAuthService` + `MockAuthService` are the auth seam for phase 2.
