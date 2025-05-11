package com.example.foodapp.repository

import data.Store


interface StoreRepository {
    fun getStores(): List<Store>
    fun getStoreById(id: Int): Store?
}