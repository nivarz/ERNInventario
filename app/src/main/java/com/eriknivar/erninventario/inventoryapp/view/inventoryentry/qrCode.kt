package com.eriknivar.erninventario.inventoryapp.view.inventoryentry


import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

fun startQRCodeScanner(activity: Activity, qrScanLauncher: ActivityResultLauncher<Intent>) {
    val integrator = IntentIntegrator(activity)
    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
    integrator.setPrompt("Scan a QR code")
    integrator.setCameraId(0)
    integrator.setBeepEnabled(true)
    integrator.setBarcodeImageEnabled(true)
    val scanIntent = integrator.createScanIntent()
    qrScanLauncher.launch(scanIntent)
}

fun handleQRCodeResult(resultCode: Int, data: Intent?, onResult: (String) -> Unit) {
    val result: IntentResult = IntentIntegrator.parseActivityResult(resultCode, data)
    if (result.contents != null) {
        onResult(result.contents)
    }

}