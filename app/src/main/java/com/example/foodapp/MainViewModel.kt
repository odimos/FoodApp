package com.example.foodapp


import data.BuyState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.foodapp.repository.FakeStoreRepo
import com.example.foodapp.repository.SocketRepository
import com.example.foodapp.repository.StoreRepository
import data.Answer
import data.GlobalConfig
import data.Store
import data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(){
    private val _stores = MutableStateFlow<List<Store>>(emptyList())
    val stores: StateFlow<List<Store>> = _stores;
    private val storeRepo: StoreRepository = SocketRepository() ;

    private val _buyStates = MutableStateFlow<Map<String, BuyState>>(emptyMap())
    val buyStates: StateFlow<Map<String, BuyState>> = _buyStates.asStateFlow()

    init {
        fetchStores()
    }

    fun buyProduct(
        storeName: String,
        productName: String,
        quantity: Int
    ) {
        viewModelScope.launch {

            val key = buyKey(storeName, productName)

            // mark as loading
            _buyStates.update { current ->
                current + (key to BuyState.Loading)
            }

            try {
                // perform the actual buy (suspends)
                val result = buy(storeName, productName, quantity)

                // mark success
                _buyStates.update { current ->
                    current + (key to BuyState.Success(result))
                }

                delay(1000) // duration success UI is shown
                _buyStates.update { it + (key to BuyState.Idle) }

            } catch (e: Exception) {
                // mark error
                _buyStates.update { current ->
                    current + (key to BuyState.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }


    private fun buyKey(storeName: String, productName: String) = "$storeName|$productName";
    fun getBuyState(storeName: String, productName: String): BuyState =
        buyStates.value[buyKey(storeName, productName)] ?: BuyState.Idle

    public fun getStoreByName( name : String): Store? {
        return _stores.value.firstOrNull { it.storeName == name }
    }

    public suspend fun buy(storeName:String, productName: String, quantity: Int): Answer {
        Log.d("DEBUG","buy "+storeName+" "+productName+" "+quantity);
        var answer: Answer = Answer("");
        answer = withContext(Dispatchers.IO) { // move out of main thread
            storeRepo.buy(storeName, productName, quantity) // Blocking Java method here
        }

        return answer;
    }

    public suspend fun rate(storeName:String, stars: Int): Answer {
        Log.d("DEBUG","rate "+storeName+" "+stars);
        var answer: Answer = Answer("");
        viewModelScope.launch {
            try {
                answer = withContext(Dispatchers.IO) { // move out of main thread
                    storeRepo.rate(storeName, stars) // Blocking Java method here
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
            }

        }
        return answer;

    }

    public fun fetchStores(
        categories: List<String> = emptyList(),
        stars: List<Int> = emptyList(),
        prices: List<String> = emptyList(),
        lat: Double = 0.0,
        lon: Double = 0.0
    ){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { // move out of main thread
                    storeRepo.getStores(categories, stars, prices, lat, lon) // Blocking Java method here
                }
                _stores.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
                _stores.value = emptyList()
            }

        }
    }
}