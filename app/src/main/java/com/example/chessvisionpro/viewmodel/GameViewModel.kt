package com.example.chessvisionpro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessvisionpro.model.Game
import com.example.chessvisionpro.repository.GameRepository
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val gameRepository = GameRepository()

    private val _gamesLiveData = MutableLiveData<List<Game>>()
    val gamesLiveData: LiveData<List<Game>> = _gamesLiveData

    private val _currentGameLiveData = MutableLiveData<Game>()
    val currentGameLiveData: LiveData<Game> = _currentGameLiveData

    private val _analysisResult = MutableLiveData<String>()
    val analysisResult: LiveData<String> = _analysisResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun fetchUserGames(username: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = gameRepository.getUserGames(username)
            result.onSuccess { games ->
                _gamesLiveData.postValue(games)
                _error.postValue("")
            }.onFailure { exception ->
                _error.postValue(exception.message ?: "Unknown error")
            }
            _loading.postValue(false)
        }
    }

    fun loadGame(gameId: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = gameRepository.getGameById(gameId)
            result.onSuccess { game ->
                _currentGameLiveData.postValue(game)
                _error.postValue("")
            }.onFailure { exception ->
                _error.postValue(exception.message ?: "Failed to load game")
            }
            _loading.postValue(false)
        }
    }

    fun analyzePosition(fen: String, depth: Int = 20) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = gameRepository.analyzePosition(fen, depth)
            result.onSuccess { analysis ->
                _analysisResult.postValue(analysis)
                _error.postValue("")
            }.onFailure { exception ->
                _error.postValue(exception.message ?: "Analysis failed")
            }
            _loading.postValue(false)
        }
    }
}
