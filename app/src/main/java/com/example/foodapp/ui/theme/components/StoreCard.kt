package com.example.foodapp.ui.theme.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.R
import com.example.foodapp.ScreenB
import data.Store
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.math.roundToInt

@Composable
fun StoreCard(
    navController: NavHostController,
    store: Store,
) {
    // Define the fixed width for the card
    val cardheight =270.dp;

    val imageData:ByteArray = store.imageData;

    val bitmap = remember(imageData) {
        BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }
    
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(cardheight)
            .padding(8.dp)
            .clickable {
                // Perform your navigation or action here
                navController.navigate(ScreenB(store.storeName))
            },

        shape = MaterialTheme.shapes.medium  // Rounded corners, can change if needed
    ) {
        Column(
            // Padding inside the card
        ) {
            // Logo Image (replace with an actual image)
            Image(
                contentScale = ContentScale.Crop,
                //painter = painterResource(id = R.drawable.store_logo), // Replace with your logo resource
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Store Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    //.size(50.dp) // Logo size
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally) // Center the logo
            )

            Column (
                modifier = Modifier.padding(16.dp)
            ) {

                // Store name
                Text(store.getStoreName(), style = MaterialTheme.typography.titleMedium)

                // Category
                Text("Category: ${store.foodCategory}")

                // Price and Rating on the same line
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        store.priceCategory,
                        modifier = Modifier.weight(1f)
                    )  // Take up remaining space
                    Text(" ${store.stars.roundToInt()}★")
                }
            }

        }
    }

}
