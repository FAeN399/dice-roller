# D20 Dice Roller

A native Android app built with Jetpack Compose and Material 3 for rolling D20 dice with advantage/disadvantage, character ability score generation (4d6 drop lowest), and roll history.

## Features

- **D20 Roll**: Roll a 20-sided die with optional advantage or disadvantage
- **Ability Scores**: Roll six ability scores using 4d6 drop lowest method
- **Re-roll**: Re-roll individual ability scores
- **Copy Scores**: Copy ability scores as CSV to clipboard
- **History**: View last 20 D20 rolls with timestamps

## Building in GitHub Actions

This project is designed to build entirely in GitHub Actions without requiring a local Android SDK.

### Push to GitHub

```bash
cd /storage/emulated/0/projects/d20-dice-roller
git remote add origin https://github.com/YOUR_USERNAME/d20-dice-roller.git
git branch -M main
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

### Download the APK

#### Method 1: Browser (Recommended)

1. Go to your repository on GitHub
2. Click **Actions** tab
3. Click the latest successful workflow run
4. Scroll to **Artifacts** section
5. Click **app-debug** to download the APK
6. Tap the downloaded APK file and select **Install**

#### Method 2: GitHub CLI in Termux

Install GitHub CLI:
```bash
pkg install gh
```

Authenticate:
```bash
gh auth login
```

Download latest artifact:
```bash
cd /storage/emulated/0/projects/d20-dice-roller
gh run download --name app-debug --dir ~/Downloads
```

Install the APK:
```bash
termux-open ~/Downloads/app-debug.apk
```

Or if `termux-open` doesn't work:
```bash
am start -a android.intent.action.VIEW -d file:///data/data/com.termux/files/home/Downloads/app-debug.apk -t application/vnd.android.package-archive
```

## Technical Details

- **Package**: `com.maddog.d20roller`
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Build**: Kotlin DSL, Gradle 8.11.1, AGP 8.7.3

## License

MIT License - see LICENSE file for details.
