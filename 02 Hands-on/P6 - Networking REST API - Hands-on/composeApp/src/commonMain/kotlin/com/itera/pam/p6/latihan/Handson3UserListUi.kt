package com.itera.pam.p6.latihan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// Hands-on 3: Tampilkan Data di Compose dengan Loading/Error UI
// Tugas: pakai UserListStateHolder (dari Handson2Repository.kt) untuk
// menampilkan:
// - CircularProgressIndicator() selagi UiState.Loading
// - LazyColumn berisi nama & email user selagi UiState.Success
// - Pesan error + tombol "Coba Lagi" (yang memanggil ulang load()) selagi
//   UiState.Error
//
// CATATAN: fungsi ini sengaja belum menampilkan apa-apa selain placeholder —
// lengkapi semua TODO di bawah supaya UI-nya berfungsi.

@Composable
fun Handson3Screen() {
    val holder = remember { UserListStateHolder() }
    val state by holder.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        holder.load()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when (val s = state) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                LazyColumn {
                    items(s.users) { user ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(user.name, style = MaterialTheme.typography.titleMedium)
                            Text(user.email, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            is UiState.Error -> {
                Text(s.message, color = MaterialTheme.colorScheme.error)
                Button(onClick = { scope.launch { holder.load() } }) {
                    Text("Coba Lagi")
                }
            }
        }
    }
}
