package com.example.chessvisionpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chessvisionpro.model.Game
import com.example.chessvisionpro.repository.GameRepository
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)

    private val _gameList = MutableLiveData<List<Game>>()
    val gameList: LiveData<List<Game>> = _gameList

    private val _currentGame = MutableLiveData<Game?>()
    val currentGame: LiveData<Game?> = _currentGame

    private val _engineAnalysis = MutableLiveData<String>()
    val engineAnalysis: LiveData<String> = _engineAnalysis

    private val _dailyPuzzle = MutableLiveData<String>()
    val dailyPuzzle: LiveData<String> = _dailyPuzzle

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getUserGames(username: String, apiToken: String? = null, maxGames: Int = 50) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getUserGames(username, apiToken, maxGames)
            result.onSuccess { games ->
                _gameList.value = games
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun getGame(gameId: String, apiToken: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getGame(gameId, apiToken)
            result.onSuccess { game ->
                _currentGame.value = game
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun getEngineAnalysis(gameId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getEngineAnalysis(gameId)
            result.onSuccess { analysis ->
                _engineAnalysis.value = analysis
                _loading.value = false
            }.onFailure { exception ->
                _error.value = exception.message
                _loading.value = false
            }
        }
    }

    fun getDailyPuzzle() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.getDailyPuzzle()
            result.onSuccess { puzzle ->
                _dailyPuzzle.value = puzzle
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