package com.example.foodapp.ui.theme.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.foodapp.MainViewModel


@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    categories: List<String> = listOf("Pizza"),
    vm: MainViewModel
){
    var selectedCategories by remember { mutableStateOf<Set<String>>(emptySet()) }

    val prices = listOf("$", "$$", "$$$")
    var selectedPrices by remember { mutableStateOf<Set<String>>(emptySet()) }

    val stars = listOf(1,2,3,4,5)
    var selectedStars by remember { mutableStateOf<Set<Int>>(emptySet()) }


    Column (modifier = Modifier
    ){
        //Text(text = "Scrollable", fontSize = 10.sp)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 0.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = selectedCategories.contains(category),
                    onClick = {
                        selectedCategories =
                            if (selectedCategories.contains(category)) {
                                selectedCategories - category   // remove on second click
                            } else {
                                selectedCategories + category   // add
                            }
                    },
                    label = { Text(category) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(vertical = 0.dp)) {
            prices.forEach { price ->
                FilterChip(
                    selected = selectedPrices.contains(price),
                    onClick = {
                        selectedPrices =
                            if (selectedPrices.contains(price)) {
                                selectedPrices - price
                            } else {
                                selectedPrices + price
                            }
                    },
                    label = { Text(price) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Row(modifier = Modifier.padding(vertical = 0.dp)) {
            stars.forEach { star ->
                FilterChip(
                    selected = selectedStars.contains(star),
                    onClick = {
                        selectedStars =
                            if (selectedStars.contains(star)) {
                                selectedStars - star
                            } else {
                                selectedStars + star
                            }
                    },
                    label = { Text(star.toString()) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Button(onClick = {
            val categories: List<String> = selectedCategories.toList()
            val stars: List<Int> = selectedStars.toList()
            val prices: List<String> = selectedPrices.toList()

            vm.fetchStores(
                categories, stars, prices, 0.0, 0.0
            );
        }) {
            Text("Search")
        }

    }
}