package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chirkov_android.R
import com.example.chirkov_android.data.AuthStore
import com.example.chirkov_android.data.module.toCardData
import com.example.chirkov_android.data.module.toDomain
import com.example.chirkov_android.ui.components.ProductCard
import com.example.chirkov_android.ui.components.ProductCardData
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.viewModel.CatalogViewModel
import com.example.chirkov_android.ui.viewModel.CatalogViewModelFactory

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    modifier: Modifier = Modifier,
    initialCategoryTitle: String = "Все",
    onBackClick: () -> Unit = {},
    onProductClick: (ProductCardData) -> Unit = {}
) {
    val categories by viewModel.categories.collectAsState()
    val selected by viewModel.selectedIndex.collectAsState()
    val productsState by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val favouriteIds by viewModel.favouriteIds.collectAsState()

    LaunchedEffect(initialCategoryTitle) {
        viewModel.setInitialCategoryTitle(initialCategoryTitle)
    }

    val headerTitle = categories.getOrNull(selected)?.title ?: initialCategoryTitle

    val cardItems = productsState.map { dto ->
        dto.toDomain().toCardData(
            isFavorite = favouriteIds.contains(dto.id),
            onFavoriteClick = { viewModel.toggleFavourite(dto.id) }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .statusBarsPadding()
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
                text = headerTitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.Category),
            fontSize = 16.sp,
            style = CustomTheme.typography.BodyMedium16,
            color = CustomTheme.colors.text,
        )

        Spacer(Modifier.height(12.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(categories) { index, cat ->
                val isSelected = index == selected
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSelected) Accent else Block)
                        .clickable { viewModel.onCategorySelected(index) }
                        .padding(horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat.title,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) Background else SubTextDark
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "...")
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp, top = 4.dp)
            ) {
                items(cardItems) { item ->
                    ProductCard(
                        data = item,
                        modifier = Modifier.clickable { onProductClick(item) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CatalogScreenPreview() {
//    CatalogScreen()
}