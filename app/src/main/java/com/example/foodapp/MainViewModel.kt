package com.example.foodapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.repository.FakeStoreRepo
import com.example.foodapp.repository.SocketRepository
import com.example.foodapp.repository.StoreRepository
import data.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(){
    private val _stores = MutableStateFlow<List<Store>>(emptyList())
    val stores: StateFlow<List<Store>> = _stores;

    private val storeRepo: StoreRepository = SocketRepository() ;

    init {
        fetchStores()
    }

    public fun getStoreByName( name : String): Store? {
        return _stores.value.firstOrNull { it.storeName == name }
    }

    public fun fetchStores(
        category: String = "",
        stars: Int = 0,
        price: String = "",
        lat: Double = 0.0,
        lon: Double = 0.0
    ){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { // move out of main thread
                    storeRepo.getStores(category, stars, price, lat, lon) // Blocking Java method here
                }
                _stores.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
                _stores.value = emptyList()
            }

        }
    }
}