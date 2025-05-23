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
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val prices = listOf("$", "$$", "$$$")
    var selectedPrice by remember { mutableStateOf<String?>(null) }

    val stars = listOf(1,2,3,4,5)
    var selectedStars by remember { mutableStateOf<Int?>(null) }


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
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = if (selectedCategory == category) null else category
                    },
                    label = { Text(category) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(vertical = 0.dp)) {
            prices.forEach { price ->
                FilterChip(
                    selected = selectedPrice == price,
                    onClick = {
                        selectedPrice = if (selectedPrice == price) null else price
                    },
                    label = { Text(price) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(vertical = 0.dp)) {
            stars.forEach { stars ->
                FilterChip(
                    selected = selectedStars == stars,
                    onClick = {
                        selectedStars = if (selectedStars == stars) null else stars
                    },
                    label = { Text(stars.toString()) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Button(onClick = {
            val category: String = selectedCategory ?: ""
            val stars: Int = selectedStars ?: 0
            val price: String = selectedPrice?:"";

            vm.fetchStores(
                category, stars, price, 0.0, 0.0
            );
        }) {
            Text("Search")
        }

    }
}