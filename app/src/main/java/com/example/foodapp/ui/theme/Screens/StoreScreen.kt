package com.example.foodapp.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.MainViewModel
import com.example.foodapp.R
import data.Product
import data.Store

@Composable
fun StoreScreen(
    vm: MainViewModel,
    innerPadding: Dp ,
    name: String,
){

    // get store
     val store:Store= vm.getStoreByName(name)!!;

    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        // Store Image
        Image(
            painter = painterResource(id = R.drawable.store_logo),
            contentDescription = "Store Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp,0.dp,0.dp,10.dp),
            contentScale = ContentScale.Crop
        )

        // Store Name
        Text(
            text = store.storeName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column (modifier = Modifier
            .padding(0.dp,0.dp,0.dp,50.dp)) {
            // LazyColumn for Products
            LazyColumn(modifier = Modifier.fillMaxSize()
            ) {
                items(store.products) { product ->
                    ProductItem(product = product)
                }
            }
        }

    }

}

@Composable
fun ProductItem(product: Product) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Row to contain name/price column and the "Buy" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Space between the name/price and the button
        ) {
            // Column containing the product name and price
            Column(
                modifier = Modifier.weight(1f) // This allows the column to take available space
            ) {
                // Product name
                Text(
                    text = product.productName,
                )
                // Product price
                Text(
                    text = "$${product.price}",
                    modifier = Modifier.padding(top = 4.dp) // Space between name and price
                )
            }

            // Buy button
            Button(onClick = { /* Handle Buy action */ }) {
                Text(text = "Buy")
            }
        }
    }
}


//To Fix the lag just use release not debugging (next to red tetragon) menu
//val store4 = Store(name = "BBQ Corner", category = "BBQ", price = "$$", stars = 4, imageID = R.drawable.store_logo,
//listOf(
//Product(name = "Classic Pizza", price = 8.99),
//Product(name = "Vegan Delight", price = 9.49),
//Product(name = "BBQ Feast", price = 12.00),
//Product(name = "Pepperoni Inferno", price = 11.50),
//Product(name = "Cheesy Overload", price = 10.75),
//Product(name = "Hawaiian Twist", price = 9.99),
//Product(name = "Margherita Supreme", price = 8.25),
//Product(name = "Spicy Paneer", price = 10.10),
//Product(name = "Mediterranean Veg", price = 9.80),
//Product(name = "Mushroom Magic", price = 8.60),
//Product(name = "Tandoori Chicken", price = 11.20),
//Product(name = "Smokey Ranch", price = 12.40),
//Product(name = "Four Cheese", price = 10.90),
//Product(name = "Farmhouse Classic", price = 9.30),
//Product(name = "Peri Peri Chicken", price = 11.75),
//Product(name = "Garlic Explosion", price = 8.40),
//Product(name = "Buffalo Special", price = 12.10),
//Product(name = "Veggie Supreme", price = 9.70),
//Product(name = "Meat Lovers", price = 13.25),
//Product(name = "Sweet Corn Delight", price = 8.20)
//)
//)
//
//StoreScreen(innerPadding,store4)