package com.example.foodapp.ui.theme.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.foodapp.R
import data.Store

@Composable
fun StoresList(
    navController: NavHostController,
    stores: List<Store>,
){
    LazyColumn(modifier = Modifier) {
        items(stores) { store ->
            StoreCard(
                navController,
                store = store
            )
        }
    }
}