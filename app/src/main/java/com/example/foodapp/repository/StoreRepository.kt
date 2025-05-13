package com.example.foodapp.repository

import data.Store


interface StoreRepository {
    fun getStores(
        category: String,
        stars: Int,
        price: String,
        lat: Double,
        lon: Double
    ): List<Store>
    fun getStoreById(id: Int): Store?
}