package com.example.chessvisionpro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chessvisionpro.ui.GameActivity
import com.example.chessvisionpro.ui.AnalysisActivity
import com.example.chessvisionpro.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userNameView: TextView
    private lateinit var statsView: TextView
    private lateinit var playButton: Button
    private lateinit var analyzeButton: Button
    private lateinit var visionButton: Button

    private val cameraPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startActivity(Intent(this, GameActivity::class.java))
        } else {
            Toast.makeText(this, "Camera permission required for board vision", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setupObservers()
        requestRequiredPermissions()
        loadUserData()
    }

    private fun initViews() {
        userNameView = findViewById(R.id.user_name)
        statsView = findViewById(R.id.stats_view)
        playButton = findViewById(R.id.btn_play)
        analyzeButton = findViewById(R.id.btn_analyze)
        visionButton = findViewById(R.id.btn_vision)

        playButton.setOnClickListener { startGameActivity() }
        analyzeButton.setOnClickListener { startAnalysisActivity() }
        visionButton.setOnClickListener { startBoardVisionActivity() }
    }

    private fun setupObservers() {
        userViewModel.userLiveData.observe(this) { user ->
            userNameView.text = "Welcome, ${user.username}"
            statsView.text = "Rating: ${user.rating} | Games: ${user.gameCount}"
        }

        userViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            userViewModel.fetchUserProfile()
        }
    }

    private fun requestRequiredPermissions() {
        val requiredPermissions = mutableListOf(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        val missingPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                missingPermissions.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startGameActivity() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    private fun startAnalysisActivity() {
        startActivity(Intent(this, AnalysisActivity::class.java))
    }

    private fun startBoardVisionActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(this, GameActivity::class.java).apply {
                putExtra("vision_mode", true)
            })
        } else {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadUserData()
            } else {
                Toast.makeText(this, "Permissions required to use the app", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
