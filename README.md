# 코인 사장님

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-blue.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.12.0-4285F4.svg)](https://www.jetbrains.com/compose-multiplatform/)
[![Gradle](https://img.shields.io/badge/Gradle-9.6.0-02303A.svg)](https://gradle.org/)
[![Android Gradle Plugin](https://img.shields.io/badge/AGP-9.4.0-3DDC84.svg)](https://developer.android.com/build)

[![android minSdkVersion](https://img.shields.io/badge/minSdkVersion-30-red)](https://developer.android.com/about/versions/11)
[![android compileSdkVersion](https://img.shields.io/badge/compileSdkVersion-37-red)](https://developer.android.com/)
[![android targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-37-red)](https://developer.android.com/)

[![iOS Minimum Target](https://img.shields.io/badge/iOS_Minimum_Target-18.2-red)](https://developer.apple.com/)

코인 사장님은 한국기술교육대학교 주변 상점의 사장님을 위한 매장 관리 애플리케이션입니다.

[Google Play에서 코인 사장님 다운로드](https://play.google.com/store/apps/details?id=in.koreatech.business)

[App Store에서 코인 사장님 다운로드](https://apps.apple.com/us/app/%EC%BD%94%EC%9D%B8-%EC%82%AC%EC%9E%A5%EB%8B%98/id6815296415)

## Tech Stack

- Compose Multiplatform
- Android & iOS
- Coroutine & Flow
- Multi-Module
- Clean Architecture
- Orbit-MVI
- Navigation 3
- Ktor Client & kotlinx.serialization
- Metro Dependency Injection
- Multiplatform Settings
- Coil
- FileKit
- AboutLibraries
- fastlane

## Module Structure

```text
KOIN_OWNER_MOBILE_V2
├── androidApp               # Android 애플리케이션 진입점
├── iosApp                   # iOS 애플리케이션 진입점
├── shared                   # 공통 App 구성, 앱 내비게이션과 플랫폼 간 진입점
├── core
│   ├── common               # 공통 유틸리티
│   ├── designsystem         # 공통 컴포넌트
│   ├── di                   # Metro AppScope와 DI 공통 정의
│   └── navigation           # 공통 내비게이션
├── data                     # DataSource, API, 로컬 저장소, Repository
├── domain                   # Domain Model, Repository, UseCase
└── feature
    ├── signin               # 로그인
    ├── signup               # 회원가입
    ├── home                 # 홈
    ├── menu                 # 메뉴 관리
    ├── event                # 이벤트 및 공지 관리
    ├── store                # 매장 등록, 수정, 전환 및 관리
    └── settings             # 설정, 약관 및 오픈소스 라이선스
```

## Build & Run

### Android

Android Studio에서 `androidApp` 실행 구성을 선택하거나 다음 명령어로 Debug APK를 빌드합니다.

```shell
./gradlew :androidApp:assembleDebug
```

### iOS

`iosApp/iosApp.xcodeproj`를 Xcode로 연 뒤 원하는 시뮬레이터 또는 기기에서 실행합니다.
