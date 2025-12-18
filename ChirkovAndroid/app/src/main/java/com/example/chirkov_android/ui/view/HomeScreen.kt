package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.CustomBottomBar
import com.example.chirkov_android.ui.components.NavTab
import com.example.chirkov_android.ui.components.ProductCard
import com.example.chirkov_android.ui.components.ProductCardData
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.ChirkovAndroidTheme
import com.example.chirkov_android.ui.theme.SubTextDark

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {}
) {
    var activeTab by remember { mutableIntStateOf(0) }

    val tabs = remember {
        listOf(
            NavTab(R.drawable.home, "Home"),
            NavTab(R.drawable.favorite, "Favorite"),
            NavTab(R.drawable.orders, "Orders"),
            NavTab(R.drawable.profile, "Profile")
        )
    }

    val categories = remember { listOf("Все", "Outdoor", "Tennis") }
    var selectedCategory by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }

    val popularProducts = remember {
        listOf(
            ProductCardData(
                imageRes = R.drawable.nike,
                label = "BEST SELLER",
                title = "Nike Air Max",
                price = "₽752.00"
            ),
            ProductCardData(
                imageRes = R.drawable.nike,
                label = "BEST SELLER",
                title = "Nike Air Max",
                price = "₽752.00"
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 90.dp)
        ) {
            // ===== Header =====
            Text(
                text = stringResource(R.string.Home),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // ===== Search + Filter =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(stringResource(R.string.Search), color = SubTextDark, fontSize = 12.sp)},
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Block,
                        unfocusedContainerColor = Block,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )

                Spacer(Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Accent)
                        .clickable { /* TODO filter */ },
                    contentAlignment = Alignment.Center
                ) {
                    // Поставь свою иконку фильтра
                    Image(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ===== Categories =====
            Text(
                text = stringResource(R.string.Category),
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 16.sp
            )

            Spacer(Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categories.indices.toList()) { idx ->
                    CategoryChip(
                        text = categories[idx],
                        selected = selectedCategory == idx,
                        onClick = { selectedCategory = idx }
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ===== Popular header =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.Popular),
                    fontSize = 16.sp,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.All),
                    fontSize = 12.sp,
                    color = Accent,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }

            Spacer(Modifier.height(12.dp))

            // ===== Popular grid (2 columns) =====
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp) // как на макете: небольшой блок с 2 карточками
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                userScrollEnabled = false
            ) {
                items(popularProducts) { product ->
                    ProductCard(
                        data = product,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ===== Promo header =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.Акции),
                    fontSize = 16.sp,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.All),
                    fontSize = 12.sp,
                    color = Accent,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }

            Spacer(Modifier.height(12.dp))

            // ===== Promo banner (one image) =====
            Image(
                painter = painterResource(id = R.drawable.promo), // поставь свой баннер
                contentDescription = "Promo",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // ===== Bottom bar (your component) =====
        CustomBottomBar(
            tabs = tabs,
            fabIcon = R.drawable.shop,
            onFabClick = onFabClick,
            activeTab = activeTab,
            onTabClick = {
                activeTab = it
                onTabSelected(it)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) Block else Block.copy(alpha = 0.6f))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (selected) Color.Black else SubTextDark
        )
    }
}

@Preview()
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
