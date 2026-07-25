package com.example.chessvisionpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chessvisionpro.R
import com.example.chessvisionpro.viewmodel.GameViewModel

class AnalysisActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        observeAnalysisData()
    }

    private fun observeAnalysisData() {
        gameViewModel.engineAnalysis.observe(this) { analysis ->
            // Update UI with analysis data
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