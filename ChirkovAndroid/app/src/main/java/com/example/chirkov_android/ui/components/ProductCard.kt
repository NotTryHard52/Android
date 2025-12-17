package com.example.chirkov_android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Hint

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    title: String,
    price: String,
    isBestSeller: Boolean,
    imageRes: Int,
    onFavoriteClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {
        Box(modifier = Modifier.padding(12.dp)) {

            // Favorite icon
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = "Favorite",
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {

                // Product image
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (isBestSeller) {
                    Text(
                        text = "BEST SELLER",
                        color = Accent,
                        fontSize = 12.sp,
                    )
                }

                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Hint
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = price,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Image( painter = painterResource(id = R.drawable.add), contentDescription = "Add", modifier = Modifier .size(36.dp) .clickable { onAddClick() } )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    ProductCard(
        title = "Nike Air Max",
        price = "₱752.00",
        isBestSeller = true,
        imageRes = R.drawable.nike,
        onFavoriteClick = { },
        onAddClick = { }
    )
}