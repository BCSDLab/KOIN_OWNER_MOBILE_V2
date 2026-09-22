# AGENTS.md

## Project Overview

KOIN Business: A Business management service for KOREATECH store owners, built with Kotlin Multiplatform (Android + iOS).

## Project Structure

This repo uses a clean architecture with MVI patterns on Kotlin Multiplatform:

- `androidApp/` - Android application entry point
- `iosApp/` - iOS application entry point
- `shared/` - Shared KMP module that wires features together and exposes the iOS framework
- `domain/` - Domain layer module that contains Repository interfaces, use cases, domain models
- `data/` - Data layer module that contains Repository implementations, API services, DTOs
- `core/` - Core modules with shared utilities and infrastructure
    - `core/common` - Common utilities
    - `core/di` - Dependency injection graph (Metro)
    - `core/designsystem` - Theme, components, generated string resources
    - `core/navigation` - Navigation scaffolding (Navigation 3)
- `feature/` - Feature modules (Compose Multiplatform UI + Orbit MVI ViewModels)
    - `feature:event`, `feature:home`, `feature:menu`, `feature:settings`, `feature:signin`, `feature:signup`, `feature:store`

## Must-Follow Rules

- ViewModels must call use cases, never repositories directly.
- All UI is Jetpack Compose Multiplatform (`commonMain`); do not write platform-specific UI in `androidMain`/`iosMain` unless unavoidable.
- Use Metro for dependency injection (no Hilt/Koin); ViewModels are resolved with `metroxViewModel()`.
- Use Orbit MVI for ViewModel/state pattern: `collectAsState()` for state, `collectSideEffect` for one-off events, as established in `feature` modules.
- Domain must have zero Android dependencies; it is a pure Kotlin module.
- For new repository and use case code, prefer `Result<T>` and `operator fun invoke(...)`.
- Kotlin Multiplatform targets: `androidTarget`, `iosArm64`, `iosSimulatorArm64`. New platform-specific code must cover all targets.
- Use backtick-escaped `in` in package declarations and imports (e.g. `` `in`.koreatech.business ``).
- Keep public APIs explicitly typed.
- Localized strings live in `core/designsystem` generated resources (`Res.string.*`); do not hardcode user-facing text.

## Build Commands

```bash
# Assemble the Android debug app
./gradlew :androidApp:assembleDebug

# Run code formatting and lint checks
./gradlew ktlintCheck

# Fix code formatting and lint
./gradlew ktlintFormat

# Run common tests
./gradlew allTests
```

## Key Guidelines for Agents

1. **Compose First:** Build new UI with Compose Multiplatform in `commonMain`.
2. **Architecture:** Respect module boundaries. Domain must be pure Kotlin; features depend on domain only, never on data.
3. **Platform Splits:** Place code preferentially in `commonMain`; keep `androidMain`/`iosMain` minimal behind `expect`/`actual`.
4. **Code Style:** Use Kotlin naming conventions and keep import order aligned with Android Studio/ktlint defaults.
5. **Version Catalog:** Declare dependencies in `gradle/libs.versions.toml`, not inline versions.
6. **Navigation:** Use Navigation 3 conventions already set up in `core/navigation` and `shared`.
