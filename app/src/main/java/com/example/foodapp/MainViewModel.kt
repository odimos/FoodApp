package com.example.foodapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.repository.FakeStoreRepo
import com.example.foodapp.repository.SocketRepository
import com.example.foodapp.repository.StoreRepository
import data.Store
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _stores = MutableStateFlow<List<Store>>(emptyList())
    val stores: StateFlow<List<Store>> = _stores;

    private val storeRepo: StoreRepository = SocketRepository() ;

    init {
        fetchStores()
    }

    private fun fetchStores(){
        viewModelScope.launch {
            delay(1000);
            _stores.value = storeRepo.getStores();
        }
    }
}