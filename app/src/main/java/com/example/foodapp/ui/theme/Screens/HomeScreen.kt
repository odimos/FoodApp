package com.example.foodapp.ui.theme.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.foodapp.MainViewModel
import com.example.foodapp.ui.theme.components.FilterSection
import com.example.foodapp.ui.theme.components.StoresList

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel
){
    Column(

    ) {
        val categories = listOf("Pizza", "Sushi", "Burger", "Vegan", "keke",
            "kekrkr", "popates", "sfoliates", "falafel");
        FilterSection(
            Modifier.padding(innerPadding),
            categories
        )

        val stores by viewModel.stores.collectAsState()
        StoresList(stores)
    }
}