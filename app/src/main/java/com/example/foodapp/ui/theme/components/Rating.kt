package com.example.foodapp.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Rating(
    rating: Int = 0,
    onRatingChanged: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    starSize: Dp = 32.dp,
    filledColor: Color = Color.Yellow,
    unfilledColor: Color = Color.Gray,
    rate: (Int)-> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(), // Fill the width so it can align content
        horizontalArrangement = Arrangement.End // Align children to the right
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star $i",
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        onRatingChanged(i);
                        rate(i);
                    },
                tint = if (i <= rating) filledColor else unfilledColor
            )
        }
    }
}
