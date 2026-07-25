package com.example.chessvisionpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chessvisionpro.R
import com.example.chessvisionpro.viewmodel.GameViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        observeGameData()
    }

    private fun observeGameData() {
        gameViewModel.currentGame.observe(this) { game ->
            game?.let {
                // Update UI with game data
            }
        }

        gameViewModel.error.observe(this) { error ->
            error?.let {
                // Show error message
            }
        }

        gameViewModel.loading.observe(this) { loading ->
            // Show/hide loading indicator
        }
    }
}