package com.example.janaushadhifinder.data

import android.content.Context
import com.example.janaushadhifinder.model.Medicine
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

class MedicineRepository(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    fun getMedicines(): List<Medicine> {
        return try {
            val inputStream = context.assets.open("medicines.json")
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            json.decodeFromString<List<Medicine>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
