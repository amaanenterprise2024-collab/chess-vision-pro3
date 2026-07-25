package com.example.chessvisionpro.util

object Constants {
    const val LICHESS_API_BASE_URL = "https://lichess.org"
    const val SHARED_PREF_NAME = "chess_vision_prefs"
    const val SHARED_PREF_TOKEN_KEY = "lichess_api_token"
    const val SHARED_PREF_USERNAME_KEY = "username"
    
    // Game time controls
    const val TIME_BULLET = "bullet"
    const val TIME_BLITZ = "blitz"
    const val TIME_RAPID = "rapid"
    const val TIME_CLASSICAL = "classical"
    
    // Board settings
    const val DEFAULT_BOARD_SIZE = 8
    const val DEFAULT_SQUARE_SIZE = 50
}
