package com.example.chessvisionpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chessvisionpro.model.User
import com.example.chessvisionpro.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _userProfile = MutableLiveData<User?>()
    val userProfile: LiveData<User?> = _userProfile

    private val _puzzleActivity = MutableLiveData<String>()
    val puzzleActivity: LiveData<String> = _puzzleActivity

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getCurrentUser(apiToken: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getCurrentUser(apiToken)
            result.onSuccess { user ->
                _currentUser.value = user
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun getUserProfile(username: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getUserProfile(username)
            result.onSuccess { user ->
                _userProfile.value = user
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun getPuzzleActivity(apiToken: String, max: Int = 50) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getPuzzleActivity(apiToken, max)
            result.onSuccess { activity ->
                _puzzleActivity.value = activity
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}