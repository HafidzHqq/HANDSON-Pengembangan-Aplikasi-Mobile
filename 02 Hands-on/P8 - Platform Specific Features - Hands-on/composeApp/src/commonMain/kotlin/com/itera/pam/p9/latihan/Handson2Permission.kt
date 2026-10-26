package com.itera.pam.p9.latihan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itera.pam.p9.hasCameraPermission

// Hands-on 2: Permission Check (Platform-Aware)
// `hasCameraPermission()` sudah diimplementasikan lewat expect/actual: cek
// ASLI di Android (ContextCompat.checkSelfPermission), stub `true` di
// iOS/Desktop karena model permission-nya berbeda dan di luar cakupan
// hands-on ini.

@Composable
fun Handson2Screen() {
    val permissionGranted = hasCameraPermission()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Hands-on 2: Permission Check")
        Spacer(modifier = Modifier.height(8.dp))
        val status = if (permissionGranted) "DIBERIKAN (Granted)" else "DITOLAK / BELUM DIBERIKAN (Denied)"
        Text("Status izin kamera: $status")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* simulasi tombol minta izin, di luar cakupan hands-on ini */ }) {
            Text("Simulasikan Minta Izin")
        }
    }
}
