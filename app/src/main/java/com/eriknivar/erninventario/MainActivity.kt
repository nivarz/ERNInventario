package com.eriknivar.erninventario

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import com.eriknivar.erninventario.inventoryapp.view.inventoryentry.ReadQRCode
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : ComponentActivity() {

    private val qrCodeContent = mutableStateOf("")

    private lateinit var qrScanLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        qrScanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (intentResult.contents != null) {
                qrCodeContent.value = intentResult.contents
            }
        }

        setContent {
            ReadQRCode({ startQRCodeScanner(this) }, qrCodeContent)
        }
    }

    private fun startQRCodeScanner(activity: Activity) {
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
