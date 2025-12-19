package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chirkov_android.R
import com.example.chirkov_android.data.AuthStore
import com.example.chirkov_android.data.module.toCardData
import com.example.chirkov_android.data.module.toDomain
import com.example.chirkov_android.ui.components.CustomBottomBar
import com.example.chirkov_android.ui.components.ProductCard
import com.example.chirkov_android.ui.components.ProductCardData
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.viewModel.CatalogViewModel
import com.example.chirkov_android.ui.viewModel.CatalogViewModelFactory
import com.example.chirkov_android.ui.components.NavTab

@Composable
fun FavoriteScreen(
    viewModel: CatalogViewModel,
    authStore: AuthStore,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onProductClick: (ProductCardData) -> Unit = {},
    onTabSelected: (Int) -> Unit = {}
) {
    val favoriteProducts by viewModel.favoriteProducts.collectAsState()
    val favouriteIds by viewModel.favouriteIds.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavoriteProducts()
    }

    val cardItems = favoriteProducts.map { dto ->
        dto.toDomain().toCardData(
            isFavorite = favouriteIds.contains(dto.id),
            onFavoriteClick = { viewModel.toggleFavourite(dto.id) }
        )
    }

    // состояние активной вкладки: 1 = Favorite (как и в HomeScreen)
    val tabs = listOf(
        NavTab(R.drawable.home, "Home"),
        NavTab(R.drawable.favorite, "Favorite"),
        NavTab(R.drawable.orders, "Orders"),
        NavTab(R.drawable.profile, "Profile")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 90.dp, start = 16.dp, end = 16.dp, top = 12.dp)
        ) {
            // верхний бар
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
                    text = "Избранное",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 32.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(16.dp))

            if (cardItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "У вас пока нет избранных товаров",
                        color = SubTextDark,
                        fontSize = 14.sp
                    )
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

        CustomBottomBar(
            tabs = tabs,
            fabIcon = R.drawable.shop,
            onFabClick = {  },
            activeTab = 1,
            onTabClick = onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
