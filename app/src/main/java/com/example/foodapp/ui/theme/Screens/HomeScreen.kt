package com.example.foodapp.ui.theme.Screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.MainViewModel
import com.example.foodapp.ui.theme.components.FilterSection
import com.example.foodapp.ui.theme.components.StoresList

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavHostController
){
    Log.d("DEBUG", "----")
    Column(
        modifier = Modifier
            .padding(
                6.dp,40.dp, 6.dp, 50.dp
            )
    ) {
        val categories = listOf("Pizza", "Sushi", "Burger", "Fast Food");

        FilterSection(
            Modifier.padding(0.dp),
            categories,
            viewModel
        )

        val stores by viewModel.stores.collectAsState()
        StoresList(
            navController,
            stores
        )
    }
}