package com.example.chessvisionpro.repository

import android.content.Context
import com.example.chessvisionpro.api.RetrofitClient
import com.example.chessvisionpro.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val context: Context) {
    private val lichessService = RetrofitClient.getLichessService(context)

    suspend fun getCurrentUser(apiToken: String): Result<User> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getAccount(apiToken)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getUserProfile(username: String): Result<User> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getUserProfile(username)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getPuzzleActivity(apiToken: String, max: Int = 50): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = lichessService.getPuzzleActivity(max = max, token = apiToken)

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