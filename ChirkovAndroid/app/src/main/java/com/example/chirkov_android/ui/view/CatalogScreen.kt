package com.example.chirkov_android.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ProductCard
import com.example.chirkov_android.ui.components.ProductCardData
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.theme.Accent

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onProductClick: (ProductCardData) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(1) } // 0 = Все, 1 = Outdoor, 2 = Tennis

    val categories = listOf("Все", "Outdoor", "Tennis")

    val products = remember {
        // Пока статический список для примера
        List(8) {
            ProductCardData(
                imageRes = R.drawable.nike,
                label = "BEST SELLER",
                title = "Nike Air Max",
                price = "₽752.00"
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Block)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = "Outdoor",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(16.dp))

        // Категории заголовок
        Text(
            text = "Категории",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SubTextDark
        )

        Spacer(Modifier.height(12.dp))

        // Category tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEachIndexed { index, title ->
                val isSelected = index == selectedCategory
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            if (isSelected) Accent else Block
                        )
                        .clickable { selectedCategory = index },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) Background else SubTextDark
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Grid с карточками
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp, top = 4.dp)
        ) {
            items(products) { item ->
                ProductCard(
                    data = item,
                    modifier = Modifier.clickable { onProductClick(item) }
                )
            }
        }
    }
}

@Preview()
@Composable
private fun CatalogScreenPreview() {
    CatalogScreen()
}