# Tournament Arena - build toolchain notes (phase 1A)

Everything is installed under `/opt`. **Never install a JDK, Gradle or the Android SDK
under `/home`** - that filesystem only has ~290 MB. `/` has a few GB.

## Installed toolchain (this machine)

| Component | Path | Version |
|---|---|---|
| JDK | `/opt/jdk` | Temurin 17.0.20.1+1 (`/opt/jdk/bin/java`) |
| Gradle (bootstrap only) | `/opt/gradle/gradle-8.7` | 8.7 |
| Android SDK | `/opt/android-sdk` | cmdline-tools 11076708, platform-tools, platforms;android-34, build-tools;34.0.0 |
| Gradle home / dependency cache | `/opt/gradle-cache` | - |
| Downloaded archives (deleted after install) | `/opt/src` | - |

## Environment for every build

```bash
export JAVA_HOME=/opt/jdk
export ANDROID_HOME=/opt/android-sdk
export ANDROID_SDK_ROOT=/opt/android-sdk
export GRADLE_USER_HOME=/opt/gradle-cache
```

`local.properties` (untracked, git-ignored) must contain:

```
sdk.dir=/opt/android-sdk
```

`local.properties.example` is committed as the template.

## Exact commands

```bash
# 1. bootstrap the wrapper (only needed if gradle/wrapper/gradle-wrapper.jar is missing)
/opt/gradle/gradle-8.7/bin/gradle wrapper --gradle-version 8.7 --distribution-type bin

# 2. build the debug APK
cd /home/team/shared/tournament-arena
./gradlew :app:assembleDebug --no-daemon

# 3. clean (keeps /home small - build output lives in app/build)
./gradlew clean

# 4. resource / manifest lint pass
./gradlew :app:processDebugResources
```

Output APK: `app/build/outputs/apk/debug/app-debug.apk`.

## How the toolchain was installed (repeatable)

```bash
mkdir -p /opt/src /opt/jdk /opt/gradle /opt/android-sdk /opt/gradle-cache

# JDK 17 (Temurin)
curl -sL -o /opt/src/jdk17.tar.gz \
  "https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse"
tar -xzf /opt/src/jdk17.tar.gz -C /opt/jdk --strip-components=1

# Gradle 8.7
curl -sL -o /opt/src/gradle.zip "https://services.gradle.org/distributions/gradle-8.7-bin.zip"
unzip -q /opt/src/gradle.zip -d /opt/gradle

# Android command line tools + SDK packages
curl -sL -o /opt/src/cmdline-tools.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"
unzip -q /opt/src/cmdline-tools.zip -d /tmp/cmdt
mv /tmp/cmdt/cmdline-tools /opt/android-sdk/cmdline-tools/latest
yes | /opt/android-sdk/cmdline-tools/latest/bin/sdkmanager --licenses
/opt/android-sdk/cmdline-tools/latest/bin/sdkmanager \
  "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

## Gotchas

* `yes | sdkmanager --licenses` must run before any package install, otherwise the
  install blocks on the licence prompt.
* `sdkmanager` needs `JAVA_HOME` exported **inside the same shell** that runs it - a
  bare `PATH` entry is not enough, and it fails with
  `ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH`.
  `source /opt/env.sh` first.
* There is no emulator and no hardware acceleration on this host; verification is a
  compile + resource-link (`assembleDebug`), not an on-device run.
* `org.gradle.daemon=false` is set in `gradle.properties`; every task forks a
  single-use daemon, which is slower but leaves no daemons behind. Set
  `--daemon` explicitly if you want a warm build.
* Network access to `dl.google.com`, `repo1.maven.org` and `services.gradle.org` is
  required for the first build. After the cache in `/opt/gradle-cache` is warm,
  `--offline` works.
