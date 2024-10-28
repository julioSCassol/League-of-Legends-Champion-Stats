package com.example.league_of_legends_application.model

data class Item(
    val name: String,
    val description: String,
    val price: Price,
    val purchasable: Boolean,
    val icon: String
)

data class Price(
    val base: Int,
    val total: Int,
    val sell: Int
)
