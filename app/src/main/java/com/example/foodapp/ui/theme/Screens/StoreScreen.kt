package com.example.foodapp.ui.theme.Screens

import data.BuyState
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.foodapp.MainViewModel
import com.example.foodapp.R
import com.example.foodapp.ui.theme.components.Rating
import data.Product
import data.Store
import kotlinx.coroutines.launch

@Composable
fun StoreScreen(
    vm: MainViewModel,
    innerPadding: Dp ,
    name: String,
){

    // get store
     val store:Store= vm.getStoreByName(name)!!;
    val imageData:ByteArray = store.imageData;

    val bitmap = remember(imageData) {
        BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }

    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        // Store Image
        Image(
            //painter = painterResource(id = R.drawable.store_logo),
            bitmap=bitmap.asImageBitmap(),
            contentDescription = "Store Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            contentScale = ContentScale.Crop
        )



        // Store Name
        Text(//
            text = store.storeName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        var selectedRating by remember { mutableIntStateOf(0) }
        var pendingRating by remember { mutableStateOf<Int?>(null) }
        var showConfirmDialog by remember { mutableStateOf(false) }

        Rating(
            rating = selectedRating,
            onRatingChanged = { newRating ->
                if (selectedRating == 0) {
                    selectedRating = newRating;
                    vm.viewModelScope.launch {
                        vm.rate(name, newRating);
                    }
                } else {
                    pendingRating = newRating
                    showConfirmDialog = true
                }
            }
        )


        if (showConfirmDialog && pendingRating != null) {
            AlertDialog(
                onDismissRequest = {
                    showConfirmDialog = false
                    pendingRating = null
                },
                title = {
                    Text("Change rating?")
                },
                text = {
                    Text("Are you sure you want to change your rating?")
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedRating = pendingRating!!
                        vm.viewModelScope.launch {
                            vm.rate(name, pendingRating!!)
                        }
                        showConfirmDialog = false
                        pendingRating = null
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        pendingRating = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        Column (modifier = Modifier
            .padding(0.dp,0.dp,0.dp,50.dp)) {
            // LazyColumn for Products
            val buyStates by vm.buyStates.collectAsState()

            LazyColumn(modifier = Modifier.fillMaxSize()
            ) {
                items(store.products) { product ->
                    if (product.availableAmount>0){
                        val key = "${store.storeName}|${product.productName}"
                        val buyState = buyStates[key] ?: BuyState.Idle

                        ProductItem(
                            product = product,
                            buyState,
                            buy = { qty ->
                                vm.buyProduct(
                                    store.storeName,
                                    product.productName,
                                    qty
                                )
                            }
                        )
                    }

                }
            }
        }

    }

}

@Composable
fun ProductItem(
    product: Product,
    buyState: BuyState,
    buy: (Int) -> Unit
) {

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
            val isBuying = buyState is BuyState.Loading
            var qty by remember(product.productName) { mutableIntStateOf(1) } // use product.id if you have it

            when (buyState){
                is BuyState.Idle -> {

                    // Buy button
                    Button(onClick = {
                        buy(qty);
                        qty = 1;
                    }) {
                        Text(text = "Buy")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { if (qty > 1) qty-- },
                            enabled = qty > 1
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                        }
                        Text(
                            text = qty.toString(),
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .widthIn(min = 12.dp, max = 48.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = { qty++ },
                            enabled = true
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
                        }
                    }
                }
                is BuyState.Loading -> {
                    Button(onClick = {
//                        buy();
                    }) {
                        Text(text = "Buying...")
                    }
                }
                is BuyState.Success -> {
                    Log.d("DEB_ANSWER", buyState.answer.message)
                    if (buyState.answer.message=="Success") {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color(0x332E7D32), // subtle green with alpha
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Bought",
                                tint = Color(0xFF2E7D32)
                            )
                        }
                    }
                    else {
                        Text(
                            text = "Failed: ${buyState.answer.message}",
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                }
                is BuyState.Error -> {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Bought",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                    )
                }

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