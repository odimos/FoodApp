package com.example.foodapp.repository

import com.example.foodapp.R
import com.example.foodapp.model.Product
import data.Store

class FakeStoreRepo : StoreRepository  {


    override fun getStores(): List<Store> {
        return emptyList()

    }

    override fun getStoreById(id: Int): Store? {
        TODO("Not yet implemented")
    }
}