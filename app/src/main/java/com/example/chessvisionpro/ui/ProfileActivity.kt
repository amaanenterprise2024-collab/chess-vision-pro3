package com.example.chessvisionpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chessvisionpro.R
import com.example.chessvisionpro.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        observeUserProfile()
    }

    private fun observeUserProfile() {
        userViewModel.currentUser.observe(this) { user ->
            user?.let {
                // Update UI with user profile data
            }
        }

        userViewModel.error.observe(this) { error ->
            error?.let {
                // Show error message
            }
        }

        userViewModel.loading.observe(this) { loading ->
            // Show/hide loading indicator
        }
    }
}