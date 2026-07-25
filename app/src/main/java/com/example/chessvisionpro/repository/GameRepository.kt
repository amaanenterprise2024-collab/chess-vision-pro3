package com.example.chessvisionpro.repository

import com.example.chessvisionpro.api.RetrofitClient
import com.example.chessvisionpro.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository {

    suspend fun getUserGames(username: String, max: Int = 50): Result<List<Game>> = withContext(Dispatchers.IO) {
        try {
            val token = RetrofitClient.getApiToken() ?: ""
            val response = RetrofitClient.lichessService.getUserGames(
                username = username,
                token = "Bearer $token",
                max = max
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(emptyList())
            } else {
                Result.failure(Exception("Failed to fetch games: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGameById(gameId: String): Result<Game> = withContext(Dispatchers.IO) {
        try {
            val token = RetrofitClient.getApiToken() ?: ""
            val response = RetrofitClient.lichessService.getGame(
                gameId = gameId,
                token = "Bearer $token"
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch game: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun analyzePosition(fen: String, depth: Int = 20): Result<String> = withContext(Dispatchers.IO) {
        try {
            val token = RetrofitClient.getApiToken() ?: ""
            val response = RetrofitClient.lichessService.analyzePosition(
                fen = fen,
                depth = depth,
                token = "Bearer $token"
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Analysis failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
