package com.example.chessvisionpro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessvisionpro.api.RetrofitClient
import com.example.chessvisionpro.model.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    suspend fun fetchUserProfile() {
        _loading.postValue(true)
        try {
            val token = RetrofitClient.getApiToken() ?: ""
            val response = RetrofitClient.lichessService.getAccount("Bearer $token")
            
            if (response.isSuccessful && response.body() != null) {
                _userLiveData.postValue(response.body())
                _error.postValue("")
            } else {
                _error.postValue("Failed to fetch user profile: ${response.code()}")
            }
        } catch (e: Exception) {
            _error.postValue("Error: ${e.message}")
        } finally {
            _loading.postValue(false)
        }
    }

    fun fetchUserGames(username: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val token = RetrofitClient.getApiToken() ?: ""
                val response = RetrofitClient.lichessService.getUserGames(
                    username = username,
                    token = "Bearer $token"
                )
                
                if (response.isSuccessful) {
                    // Parse NDJSON response
                    _error.postValue("")
                } else {
                    _error.postValue("Failed to fetch games: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
