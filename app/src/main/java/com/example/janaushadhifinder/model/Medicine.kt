package com.example.janaushadhifinder.model

import kotlinx.serialization.Serializable

@Serializable
data class Medicine(
    val id: Int,
    val brandName: String,
    val genericName: String,
    val saltComposition: String,
    val brandedPrice: Double,
    val genericPrice: Double
) {
    val savings: Double get() = brandedPrice - genericPrice
    val savingsPercentage: Double get() = (savings / brandedPrice) * 100
}
