package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.janaushadhifinder.model.Reminder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen(
    reminders: List<Reminder>,
    onAddReminder: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var medName by remember { mutableStateOf("") }
    var refillDate by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Refill Reminders") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Reminder")
            }
        }
    ) { padding ->
        if (reminders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No reminders set", color = Color.Gray)
                    Text("Tap + to add a monthly refill reminder", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reminders) { reminder ->
                    ReminderCard(reminder)
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Monthly Refill") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = medName,
                            onValueChange = { medName = it },
                            label = { Text("Medicine Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = refillDate,
                            onValueChange = { refillDate = it },
                            label = { Text("Next Refill Date (e.g. 25th Oct)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (medName.isNotBlank() && refillDate.isNotBlank()) {
                                onAddReminder(medName, refillDate)
                                medName = ""
                                refillDate = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun ReminderCard(reminder: Reminder) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = Color(0xFF388E3C)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = reminder.medicineName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Next Refill: ${reminder.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }
    }
}
