package com.itera.pam.p7.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itera.pam.p7.db.AppDatabase

// Hands-on 3: Offline-First — Tampilkan & Hapus Catatan
// Gunakan CatatanRepository dari Handson2Repository.kt (yang sudah kamu
// lengkapi) untuk menampilkan daftar catatan dalam LazyColumn, form untuk
// menambah catatan baru, dan tombol hapus per item.

@Composable
fun Handson3Screen(database: AppDatabase) {
    val repository = remember { CatatanRepository(database) }
    val catatanList by repository.getAll().collectAsState(initial = emptyList())
    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tambah Catatan", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = isi,
            onValueChange = { isi = it },
            label = { Text("Isi") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (judul.isNotBlank() && isi.isNotBlank()) {
                    repository.tambah(judul, isi)
                    judul = ""
                    isi = ""
                }
            }
        ) {
            Text("Tambah")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Daftar Catatan", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            items(catatanList, key = { it.id }) { catatan ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(catatan.judul, style = MaterialTheme.typography.titleSmall)
                            Text(catatan.isi, style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(onClick = { repository.hapus(catatan.id) }) {
                            Text("Hapus")
                        }
                    }
                }
            }
        }
    }
}
