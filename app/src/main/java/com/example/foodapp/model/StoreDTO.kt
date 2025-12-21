package com.example.foodapp.model

data class StoreDTO(
    val name: String,
    val category: String,
    val price: String,
    val stars: Int,
    val imageID: Int,
    val products: List<Product> = emptyList()
)

data class Product(
    val name: String,
    val price: Double,
)