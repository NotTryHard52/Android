package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.components.WhiteButton
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.Text as TextColor


@Composable
fun OnboardScreen(
    onStartClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Accent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Надпись сверху
            Text(
                text = stringResource(R.string.Welcome),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Block,
                textAlign = TextAlign.Center
            )

            // 2. Блок по центру: картинка + линии
            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboard_1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(375f / 302f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(Block)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(Block.copy(alpha = 0.4f))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(Block.copy(alpha = 0.4f))
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 3. Белая кнопка внизу
            WhiteButton(
                text = stringResource(R.string.Start),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onStartClick,
            )
        }
    }
}

@Preview()
@Composable
private fun OnboardScreenPreview() {
    OnboardScreen()
}
