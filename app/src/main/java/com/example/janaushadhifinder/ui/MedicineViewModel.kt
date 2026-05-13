package com.example.janaushadhifinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.janaushadhifinder.data.MedicineRepository
import com.example.janaushadhifinder.model.Medicine
import com.example.janaushadhifinder.model.Reminder
import com.example.janaushadhifinder.model.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MedicineViewModel(private val repository: MedicineRepository) : ViewModel() {

    private val _allMedicines = MutableStateFlow<List<Medicine>>(emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredMedicines = MutableStateFlow<List<Medicine>>(emptyList())
    val filteredMedicines: StateFlow<List<Medicine>> = _filteredMedicines.asStateFlow()

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders.asStateFlow()

    private val _stores = MutableStateFlow<List<Store>>(
        listOf(
            Store(1, "Jan Aushadhi Kendra - Sector 15", "Shop No 24, Market Area, Sector 15", "1.2 km", true),
            Store(2, "Jan Aushadhi Kendra - Civil Lines", "Opposite General Hospital, Civil Lines", "2.5 km", true),
            Store(3, "Jan Aushadhi Kendra - Railway Station", "Platform 1 Exit, Railway Station", "4.0 km", false),
            Store(4, "Jan Aushadhi Kendra - Model Town", "House No 122, Block B, Model Town", "5.1 km", true)
        )
    )
    val stores: StateFlow<List<Store>> = _stores.asStateFlow()

    init {
        _allMedicines.value = repository.getMedicines()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _filteredMedicines.value = emptyList()
        } else {
            // Simple fuzzy search (contains or close match)
            _filteredMedicines.value = _allMedicines.value.filter {
                it.brandName.contains(query, ignoreCase = true) ||
                it.genericName.contains(query, ignoreCase = true) ||
                it.saltComposition.contains(query, ignoreCase = true)
            }
        }
    }

    fun addReminder(medicineName: String, date: String) {
        val newReminder = Reminder(_reminders.value.size + 1, medicineName, date)
        _reminders.update { it + newReminder }
    }

    fun calculateTotalSavings(): Double {
        return _allMedicines.value.sumOf { it.savings }
    }

    companion object {
        fun provideFactory(repository: MedicineRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MedicineViewModel(repository)
            }
        }
    }
}
