package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.janaushadhifinder.model.Medicine
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    medicines: List<Medicine>,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Search Medicine") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter brand or salt name...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (medicines.isEmpty() && query.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No medicines found", color = Color.Gray)
                }
            } else if (query.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Search for a branded medicine to find its generic alternative", 
                         textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                         color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(medicines) { medicine ->
                        MedicineCard(
                            medicine = medicine,
                            onRequestStock = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Stock availability request sent for ${medicine.genericName}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: Medicine, onRequestStock: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = medicine.brandName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Generic: ${medicine.genericName}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                Surface(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "SAVE ${medicine.savingsPercentage.toInt()}%",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Composition: ${medicine.saltComposition}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                PriceInfo(
                    label = "Branded Price",
                    price = medicine.brandedPrice,
                    modifier = Modifier.weight(1f),
                    isGeneric = false
                )
                PriceInfo(
                    label = "Generic Price",
                    price = medicine.genericPrice,
                    modifier = Modifier.weight(1f),
                    isGeneric = true
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onRequestStock,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Request Stock Availability")
            }
        }
    }
}

@Composable
fun PriceInfo(label: String, price: Double, modifier: Modifier, isGeneric: Boolean) {
    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(
            text = "₹${String.format(Locale.getDefault(), "%.2f", price)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (isGeneric) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
        )
    }
}
