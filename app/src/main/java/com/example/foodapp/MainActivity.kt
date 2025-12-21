package com.example.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import com.example.foodapp.ui.theme.FoodAppTheme
//import com.example.foodapp.ui.theme.Screens.HomeScreen

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.example.foodapp.ui.theme.Screens.HomeScreen
import com.example.foodapp.ui.theme.Screens.StoreScreen
import data.Store
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels() // ViewModel tied to Activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FoodAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //StoreScreen(innerPadding,store4)
                    val navController = rememberNavController();
                    MyNavHost(
                        navController,
                        viewModel
                    )
                }
            }


        }
    }
}

@Composable
fun MyNavHost(
    navController: NavHostController,
    vm: MainViewModel
){
    NavHost(
        navController,
        startDestination= Home
    ) {
        composable<Home> {
            HomeScreen(
                vm,
                navController
            )
        }
        composable<ScreenB> {
            val args = it.toRoute<ScreenB>();
            val name = args.name!!;

            StoreScreen(
                vm,
                0.dp,
                args.name
            );
        }
    }
}



@Serializable
object Home

@Serializable
data class ScreenB(
    val name: String?,
)