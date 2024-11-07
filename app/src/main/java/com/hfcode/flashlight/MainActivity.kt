package com.hfcode.flashlight

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest

class MainActivity : ComponentActivity() {

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 1001  // Define the request code
    }

    private var isFlashlightOn: Boolean = false  // Tracks the flashlight state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the app has camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Permission granted, check flashlight state
            checkFlashlightState()
        }

        // Set up the button click listener
        findViewById<Button>(R.id.TogB).setOnClickListener {
            toggleFlashlight()
        }
    }

    // Check if the flashlight is on or off
    private fun checkFlashlightState() {
        val flashlightUtil = FlashlightUtil(this)

        // Check the flashlight state
        isFlashlightOn = flashlightUtil.checkFlashlightState()
        if (isFlashlightOn) {
            Log.d("HfFlashL", "Flashlight is on.")
            findViewById<Button>(R.id.TogB).text = getString(R.string.Fon)
        } else {
            Log.d("HfFlashL", "Flashlight is off.")
            findViewById<Button>(R.id.TogB).text = getString(R.string.Foff)
        }
    }

    // Toggle the flashlight state
    private fun toggleFlashlight() {
        val flashlightUtil = FlashlightUtil(this)

        if (isFlashlightOn) {
            flashlightUtil.turnOffFlashlight()
            isFlashlightOn = false
            findViewById<Button>(R.id.TogB).text = getString(R.string.Foff)
        } else {
            flashlightUtil.turnOnFlashlight()
            isFlashlightOn = true
            findViewById<Button>(R.id.TogB).text = getString(R.string.Fon)
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, check flashlight state
                checkFlashlightState()
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Camera permission is required to use the flashlight.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
