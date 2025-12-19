package com.example.chirkov_android.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.WhiteButton
import com.example.chirkov_android.data.module.OnboardPage
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.theme.Raleway
import com.example.chirkov_android.ui.theme.SubTextDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardScreen(
    onStartClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val pages = remember {
        listOf(
            OnboardPage(R.drawable.onboard_1, R.string.Welcome, isFirst = true),
            OnboardPage(R.drawable.onboard_2, R.string.Journey, R.string.Description),
            OnboardPage(R.drawable.onboard_3, R.string.Strength, R.string.Room)
        )
    }

    val pagerState = rememberPagerState { pages.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF48B2E7), // 0%
                        Color(0xFF44A9DC), // 12.45%
                        Color(0xFF2B6B8B)  // 100%
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // важное изменение
        ) {

            // ===== PAGER =====
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxHeight(0.75f) // занимает ~75% высоты
            ) { page ->

                val item = pages[page]

                if (page == 0) {
                    // ===== 1 СЛАЙД =====
                    AnimatedVisibility(
                        visible = pagerState.currentPage == page,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(29.dp))

                            Text(
                                text = stringResource(item.title),
                                style = CustomTheme.typography.HeadingBold30,
                                color = CustomTheme.colors.block,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(122.dp))

                            Image(
                                painter = painterResource(item.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(375f / 302f),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(26.dp))
                        }
                    }
                } else {
                    // ===== 2–3 СЛАЙД =====
                    AnimatedVisibility(
                        visible = pagerState.currentPage == page,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(13.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(37.dp))

                            Image(
                                painter = painterResource(item.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(375f / 302f),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(60.dp))

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(13.dp)
                            ) {
                                Text(
                                    text = stringResource(item.title),
                                    style = CustomTheme.typography.HeadingRegular32,
                                    color = CustomTheme.colors.block,
                                    textAlign = TextAlign.Center
                                )

                                item.description?.let {
                                    Text(
                                        text = stringResource(it),
                                        style = CustomTheme.typography.BodyRegular16,
                                        color = CustomTheme.colors.subTextDark,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                }
            }

            // ===== ИНДИКАТОРЫ =====
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { index ->
                    val selected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .width(if (selected) 40.dp else 20.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(if (selected) Block else Block.copy(alpha = 0.4f))
                    )
                    if (index != pagerState.pageCount - 1) Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp)) // расстояние до кнопки

            WhiteButton(
                text = stringResource(if (pagerState.currentPage == 0) R.string.Start else R.string.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onStartClick()
                    }
                }
            )

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Preview()
@Composable
private fun OnboardScreenPreview() {
    OnboardScreen()
}
