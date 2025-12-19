package com.example.chirkov_android.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.R
import com.example.chirkov_android.data.module.ProductDto
import com.example.chirkov_android.data.module.toDomain
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.theme.Text as TextColor
import com.example.chirkov_android.ui.viewModel.CatalogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    startProductId: String,
    viewModel: CatalogViewModel,
    onBackClick: () -> Unit = {}
) {
    val products by viewModel.products.collectAsState()
    val favouriteIds by viewModel.favouriteIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllProductsIfNeeded()
    }

    when {
        isLoading && products.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Загрузка...", color = TextColor)
            }
            return
        }

        error != null && products.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error ?: "Ошибка", color = TextColor)
            }
            return
        }

        products.isEmpty() -> return
    }

    val initialIndex = remember(products, startProductId) {
        val idx = products.indexOfFirst { it.id == startProductId }
        if (idx >= 0) idx else 0
    }

    val pagerState = rememberPagerState(initialPage = initialIndex) { products.size }
    val scope = rememberCoroutineScope()

    val currentDto = products[pagerState.currentPage]
    val current = currentDto.toDomain()
    val isFavorite = favouriteIds.contains(currentDto.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // ===== TOP BAR (как в примере) =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Block)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = "Sneaker Shop",
                modifier = Modifier.weight(1f),
                color = TextColor,
                fontSize = 18.sp
            )

            IconButton(
                onClick = { viewModel.toggleFavourite(currentDto.id) },
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Block)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Fav",
                    tint = if (isFavorite) Accent else TextColor
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // ===== TITLE / CATEGORY / PRICE =====
        Text(text = current.title, color = TextColor, fontSize = 26.sp)
        Text(
            text = "Men's Shoes",
            color = SubTextDark,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = current.price,
            color = TextColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(18.dp))

        // ===== PODIUM + PAGER =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.podium),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(bottom = 20.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalAlignment = Alignment.Bottom
            ) { page ->
                // TODO: когда появится url с сервера — заменить на AsyncImage/Coil
                Image(
                    painter = painterResource(id = R.drawable.nike),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1.1f),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // ===== THUMBNAILS =====
        ThumbnailsRow(
            products = products,
            selectedIndex = pagerState.currentPage,
            onSelect = { index ->
                scope.launch { pagerState.animateScrollToPage(index) }
            }
        )

        Spacer(Modifier.height(40.dp))

        // ===== DESCRIPTION + "Подробнее" (с сервера) =====
        ExpandableText(
            text = currentDto.description.orEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        // ===== BOTTOM: только favorite (без корзины) =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Block)
                    .clickable { viewModel.toggleFavourite(currentDto.id) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Fav",
                    tint = if (isFavorite) Accent else TextColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun ThumbnailsRow(
    products: List<ProductDto>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        listState.animateScrollToItem(selectedIndex)
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(products) { index, _ ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Block)
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) Accent else Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelect(index) },
                contentAlignment = Alignment.Center
            ) {
                // TODO: миниатюра с сервера → AsyncImage/Coil
                Image(
                    painter = painterResource(id = R.drawable.nike),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp).fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier
) {
    key(text) {
        var isExpanded by remember { mutableStateOf(false) }
        var isOverflowing by remember { mutableStateOf(false) }

        Column(modifier = modifier.animateContentSize()) {
            Text(
                text = text,
                color = SubTextDark,
                fontSize = 14.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { tlr ->
                    if (!isExpanded && tlr.hasVisualOverflow) {
                        isOverflowing = true
                    }
                }
            )

            if (isOverflowing || isExpanded) {
                Text(
                    text = if (isExpanded) "Скрыть" else "Подробнее",
                    color = Accent,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isExpanded = !isExpanded }
                        .align(Alignment.End)
                )
            }
        }
    }
}