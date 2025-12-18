package com.example.chirkov_android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark

data class NavTab(val iconId: Int, val title: String)

@Composable
fun CustomBottomBar(
    tabs: List<NavTab>,
    onFabClick: () -> Unit,
    fabIcon: Int? = null,
    modifier: Modifier = Modifier,
    activeTab: Int,
    onTabClick: (Int) -> Unit,
    barColor: Color = Block,
    fabColor: Color = Accent,
    iconColor: Color = SubTextDark
) {
    Box(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(barColor)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.take(2).forEachIndexed { index, tab ->
                    NavIcon(
                        tab = tab,
                        selected = activeTab == index,
                        onClick = { onTabClick(index) },
                        iconColor = iconColor
                    )
                }
            }

            Box(modifier = Modifier.size(56.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.drop(2).take(2).forEachIndexed { i, tab ->
                    val realIndex = i + 2
                    NavIcon(
                        tab = tab,
                        selected = activeTab == realIndex,
                        onClick = { onTabClick(realIndex) },
                        iconColor = iconColor
                    )
                }
            }
        }

        FabCircle(
            iconRes = fabIcon,
            onClick = onFabClick,
            background = fabColor,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-28).dp)
        )
    }
}

@Composable
private fun NavIcon(
    tab: NavTab,
    selected: Boolean,
    onClick: () -> Unit,
    iconColor: Color
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable(enabled = !selected) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(tab.iconId),
            contentDescription = tab.title,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(if (selected) Accent else iconColor)
        )
    }
}

@Composable
private fun FabCircle(
    iconRes: Int?,
    onClick: () -> Unit,
    background: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .shadow(8.dp, CircleShape)
            .background(background, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        iconRes?.let {
            Image(
                painter = painterResource(it),
                contentDescription = "FAB",
                modifier = Modifier.size(28.dp),
                colorFilter = ColorFilter.tint(Block)
            )
        }
    }
}

@Preview
@Composable
private fun CustomBottomBarPreview() {
    var active by remember { mutableStateOf(0) }
    val tabs = listOf(
        NavTab(R.drawable.home, "Home"),
        NavTab(R.drawable.favorite, "Favorite"),
        NavTab(R.drawable.orders, "Shipping"),
        NavTab(R.drawable.profile, "Profile")
    )

    Column(Modifier.background(Block).padding(bottom = 30.dp)) {
        Box(Modifier.fillMaxWidth().weight(1f))
        CustomBottomBar(
            tabs = tabs,
            fabIcon = R.drawable.shop,
            onFabClick = {},
            activeTab = active,
            onTabClick = { active = it }
        )
    }
}