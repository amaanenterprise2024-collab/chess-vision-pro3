package com.example.chessvisionpro.repository

import com.example.chessvisionpro.api.RetrofitClient
import com.example.chessvisionpro.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    suspend fun getCurrentUser(): Result<User> = withContext(Dispatchers.IO) {
        try {
            val token = RetrofitClient.getApiToken() ?: ""
            val response = RetrofitClient.lichessService.getAccount("Bearer $token")
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setApiToken(token: String) {
        RetrofitClient.setApiToken(token)
    }
}
