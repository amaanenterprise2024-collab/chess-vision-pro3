package com.example.androidtestapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var infoView: TextView

    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = perms[Manifest.permission.READ_CONTACTS] == true &&
                (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true || perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
        if (granted) {
            loadContactAndLocation()
        } else {
            infoView.text = "Permissions not granted. Please grant contacts and location permissions."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        infoView = findViewById(R.id.info)

        ensurePermissions()
    }

    private fun ensurePermissions() {
        val required = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val missing = required.filter { ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
        if (missing.isEmpty()) {
            loadContactAndLocation()
        } else {
            requestPermissions.launch(required)
        }
    }

    private fun loadContactAndLocation() {
        infoView.text = "Loading contact and location..."

        // Load contact (first phone contact found)
        var contactText = "No contacts found"
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val name = it.getString(0)
                val number = it.getString(1)
                contactText = "Contact: $name\nPhone: $number"
            }
        }

        // Load location and reverse-geocode on IO dispatcher
        val fused = LocationServices.getFusedLocationProviderClient(this)
        fused.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lifecycleScope.launch {
                    val addressText = withContext(Dispatchers.IO) {
                        try {
                            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                            val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if (list != null && list.isNotEmpty()) {
                                val addr = list[0]
                                val line = addr.getAddressLine(0)
                                "Location: $line\nLat: ${location.latitude}, Lon: ${location.longitude}"
                            } else {
                                "Location: ${location.latitude}, ${location.longitude} (no address)"
                            }
                        } catch (e: Exception) {
                            "Location: ${location.latitude}, ${location.longitude} (geocoder error)"
                        }
                    }
                    infoView.text = "$contactText\n\n$addressText"
                }
            } else {
                infoView.text = "$contactText\n\nLocation: not available"
            }
        }.addOnFailureListener { err ->
            infoView.text = "$contactText\n\nUnable to get location: ${err.message}"
        }
    }
}
