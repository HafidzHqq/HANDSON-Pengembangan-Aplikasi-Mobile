package com.itera.pam.p10.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.itera.pam.p10.MockAiRepository
import kotlinx.coroutines.launch

// Hands-on 3: AI-Powered Chat UI
// Tugas: buat UI chat sederhana: TextField input, tombol "Kirim", daftar
// bubble pesan (isUser: Boolean, text: String) yang bisa di-scroll, dan
// indikator loading saat menunggu jawaban dari AiRepository.ask()
// (pakai MockAiRepository() secara default).

data class ChatMessage(val isUser: Boolean, val text: String)

@Composable
fun Handson3Screen() {
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var input by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val repository = remember { MockAiRepository() }

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            items(messages) { message ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (message.isUser) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.secondaryContainer
                            )
                            .padding(8.dp)
                    ) {
                        Text(message.text)
                    }
                }
            }
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                label = { Text("Pesan") }
            )
            Button(
                onClick = {
                    if (input.isNotBlank() && !isLoading) {
                        val prompt = input
                        messages = messages + ChatMessage(isUser = true, text = prompt)
                        input = ""
                        isLoading = true
                        scope.launch {
                            val response = repository.ask(prompt)
                            val answerText = response.fold(
                                onSuccess = { it },
                                onFailure = { "Error: ${it.message}" }
                            )
                            messages = messages + ChatMessage(isUser = false, text = answerText)
                            isLoading = false
                        }
                    }
                }
            ) {
                Text("Kirim")
            }
        }
    }
}
