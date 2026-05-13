package com.example.janaushadhifinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.janaushadhifinder.data.MedicineRepository
import com.example.janaushadhifinder.ui.MedicineViewModel
import com.example.janaushadhifinder.ui.screens.*
import com.example.janaushadhifinder.ui.theme.JanAushadhiFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val repository = MedicineRepository(applicationContext)
        
        setContent {
            JanAushadhiFinderTheme {
                val navController = rememberNavController()
                val viewModel: MedicineViewModel = viewModel(
                    factory = MedicineViewModel.provideFactory(repository)
                )

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(onTimeout = {
                            navController.navigate("home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        })
                    }
                    composable("home") {
                        val totalSavings = viewModel.calculateTotalSavings()
                        HomeScreen(
                            onNavigateToSearch = { navController.navigate("search") },
                            onNavigateToStores = { navController.navigate("stores") },
                            onNavigateToReminders = { navController.navigate("reminders") },
                            totalSavings = totalSavings
                        )
                    }
                    composable("search") {
                        val query by viewModel.searchQuery.collectAsState()
                        val medicines by viewModel.filteredMedicines.collectAsState()
                        MedicineSearchScreen(
                            query = query,
                            onQueryChange = { viewModel.onSearchQueryChange(it) },
                            medicines = medicines,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("stores") {
                        val stores by viewModel.stores.collectAsState()
                        NearbyStoresScreen(
                            stores = stores,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("reminders") {
                        val reminders by viewModel.reminders.collectAsState()
                        ReminderScreen(
                            reminders = reminders,
                            onAddReminder = { med, date -> viewModel.addReminder(med, date) },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
