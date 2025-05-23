package com.example.foodapp.repository

import data.Answer
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
    fun buy(
        storeName:String,
        productName: String,
        quantity: Int
    ): Answer
    fun rate(
        storeName: String,
        stars: Int
    ): Answer
}

// maybe instead of answer a local answer
// status 404, 200//
// message
// result (can be empty)