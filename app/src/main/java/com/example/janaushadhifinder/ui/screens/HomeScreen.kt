package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToStores: () -> Unit,
    onNavigateToReminders: () -> Unit,
    totalSavings: Double
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jan Aushadhi Finder", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SavingsCard(totalSavings)
            }
            item {
                Text(
                    text = "Quick Services",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ServiceCard(
                        modifier = Modifier.weight(1f),
                        title = "Find Medicine",
                        icon = Icons.Default.Search,
                        onClick = onNavigateToSearch
                    )
                    ServiceCard(
                        modifier = Modifier.weight(1f),
                        title = "Nearby Stores",
                        icon = Icons.Default.LocationOn,
                        onClick = onNavigateToStores
                    )
                }
            }
            item {
                ServiceCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Medicine Reminders",
                    icon = Icons.Default.Notifications,
                    onClick = onNavigateToReminders
                )
            }
            item {
                HealthcareTipCard()
            }
        }
    }
}

@Composable
fun SavingsCard(amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Potential Savings",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "₹${String.format("%.2f", amount)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "By choosing generic alternatives",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ServiceCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HealthcareTipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF1976D2)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Health Tip",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
                Text(
                    text = "Generic medicines have the same active ingredients as branded ones and are just as effective.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
