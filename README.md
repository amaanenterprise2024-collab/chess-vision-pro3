# Chess Vision Pro - Lichess Mobile App

A powerful Android chess application that integrates with Lichess, featuring real-time game analysis, chess board vision recognition, and comprehensive game statistics.

## Features

✨ **Core Features**
- Real-time Lichess API integration
- Live game analysis and evaluation
- Chess board vision recognition (camera-based piece detection)
- User account management and statistics
- Game history and replay functionality
- Multi-variant support (Chess, Blitz, Rapid, Classical)
- Move suggestions and best move analysis

🎮 **Game Modes**
- Live games against real opponents
- Study & Training mode
- Puzzle solving
- Game analysis
- Opening preparation

🔍 **Vision & Analysis**
- AI-powered board recognition
- Real-time position evaluation
- Engine analysis (using Stockfish integration)
- Move strength assessment

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM with Coroutines
- **API**: Lichess.org REST API
- **Camera**: CameraX API
- **Image Processing**: TensorFlow Lite (board recognition)
- **Database**: Room
- **Networking**: Retrofit + OkHttp
- **UI**: Jetpack Compose (future upgrade)

## Requirements

- Android 7.0+ (API 23)
- Camera permission for board vision
- Internet connection for Lichess API

## Building the Project

1. Clone the repository
```bash
git clone https://github.com/agartalalive24-786/andriod-app-2026-akbar.git
cd andriod-app-2026-akbar
```

2. Open in Android Studio
```bash
android-studio .
```

3. Let Gradle sync and resolve dependencies

4. Configure Lichess API:
   - Get your API token from https://lichess.org/account/oauth/token
   - Add to `local.properties`: `lichess.api.token=YOUR_TOKEN`

5. Build and run:
```bash
./gradlew build
./gradlew installDebug
```

## Permissions Required

- `CAMERA` - For chess board vision recognition
- `INTERNET` - For Lichess API communication
- `READ_EXTERNAL_STORAGE` - For importing games

## API Integration

### Lichess API Endpoints Used
- `/api/account` - User profile and statistics
- `/api/games/user/{username}` - User game history
- `/api/stream/event` - Real-time game updates
- `/api/engine` - Engine analysis
- `/api/puzzles` - Daily puzzles

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/chessvisionpro/
│   │   ├── ui/
│   │   │   ├── MainActivity.kt
│   │   │   ├── GameActivity.kt
│   │   │   ├── AnalysisActivity.kt
│   │   │   └── ProfileActivity.kt
│   │   ├── api/
│   │   │   ├── LichessService.kt
│   │   │   └── RetrofitClient.kt
│   │   ├── viewmodel/
│   │   │   ├── GameViewModel.kt
│   │   │   └── UserViewModel.kt
│   │   ├── model/
│   │   │   ├── Game.kt
│   │   │   ├── User.kt
│   │   │   └── Position.kt
│   │   ├── repository/
│   │   │   └── GameRepository.kt
│   │   └── vision/
│   │       ├── BoardDetector.kt
│   │       └── PieceRecognizer.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── drawable/
│   │   └── values/
│   └── AndroidManifest.xml
└── build.gradle
```

## Notes and Current Limitations

- Board vision recognition currently uses TensorFlow Lite (Stockfish integration pending)
- Engine analysis limited to 5 seconds per position to maintain responsiveness
- Puzzle solving is read-only (training feature)
- Real-time notifications require FCM setup

## Future Enhancements

- Jetpack Compose UI redesign
- Offline game analysis
- Cloud game backup
- Social features (friends, chat)
- Custom board themes
- Move animations and haptic feedback
- Widget support for quick access

## Contributing

Fork the repository and create a pull request with your improvements.

## License

MIT License - See LICENSE file for details

## Support

For issues or feature requests, please visit: https://github.com/agartalalive24-786/andriod-app-2026-akbar/issues
