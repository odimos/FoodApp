package com.example.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.ui.theme.Screens.HomeScreen


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels() // ViewModel tied to Activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FoodAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        innerPadding,
                        viewModel
                    )
                    //StoreScreen(innerPadding,store4)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
