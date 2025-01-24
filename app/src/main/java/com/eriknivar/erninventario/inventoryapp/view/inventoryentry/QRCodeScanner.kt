package com.eriknivar.erninventario.inventoryapp.view.inventoryentry



import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.zxing.integration.android.IntentIntegrator

class QRCodeScanner(private val qrScanLauncher: ActivityResultLauncher<Intent>) {

    fun startQRCodeScanner(activity: Activity) {
        val integrator = IntentIntegrator(activity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        val scanIntent = integrator.createScanIntent()
        qrScanLauncher.launch(scanIntent)
    }
}
