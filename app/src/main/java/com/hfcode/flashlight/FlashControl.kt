package com.hfcode.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager


class FlashlightUtil(context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null// Return the current flashlight state

    // Check if the flashlight is on
    public var isFlashlightOn: Boolean = false // Tracks the flashlight state
        private set

    init {
        try {
            cameraId = cameraManager.cameraIdList[0] // Usually, the rear camera is the first one
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Turn the flashlight on
    fun turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId!!, true) // Turn on flashlight
            isFlashlightOn = true // Update state
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Turn the flashlight off
    fun turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId!!, false) // Turn off flashlight
            isFlashlightOn = false // Update state
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
    fun checkFlashlightState(): Boolean {
        return try {
            // In a real-world scenario, you might need to track the state explicitly.
            // This method would return true if the flashlight is on.
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object
}
