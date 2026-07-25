package com.example.chessvisionpro.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.chessvisionpro.api.RetrofitClient
import com.example.chessvisionpro.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val context: Context) {
    private val lichessService = RetrofitClient.getLichessService(context)

    suspend fun getUserGames(
        username: String,
        apiToken: String?,
        maxGames: Int = 50
    ): Result<List<Game>> = withContext(Dispatchers.IO) {
        try {
            val response = lichessService.getUserGames(
                username = username,
                max = maxGames,
                token = apiToken
            )

            if (response.isSuccessful) {
                Result.success(emptyList()) // Parse PGN response to List<Game>
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGame(
        gameId: String,
        apiToken: String?
    ): Result<Game> = withContext(Dispatchers.IO) {
        try {
            val response = lichessService.getGame(
                gameId = gameId,
                token = apiToken
            )

            if (response.isSuccessful) {
                Result.success(Game(
                    id = gameId,
                    userId = "",
                    createdAt = System.currentTimeMillis(),
                    status = "",
                    speed = "",
                    perf = "",
                    whitePlayer = Game.Player("", "", 0, null),
                    blackPlayer = Game.Player("", "", 0, null),
                    initialFen = "",
                    moves = "",
                    result = null,
                    winner = null,
                    rated = false,
                    variant = "standard",
                    opening = null,
                    lastMoveAt = null
                ))
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEngineAnalysis(gameId: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getEngineAnalysis(gameId)

                if (response.isSuccessful) {
                    Result.success(response.body() ?: "")
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getDailyPuzzle(): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getDailyPuzzle()

                if (response.isSuccessful) {
                    Result.success(response.body() ?: "")
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getOpeningStatistics(fen: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getOpeningStats(fen = fen)

                if (response.isSuccessful) {
                    Result.success(response.body() ?: "")
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}