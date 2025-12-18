package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.CustomBottomBar
import com.example.chirkov_android.ui.components.NavTab
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.SubTextDark

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {}
) {
    var activeTab by remember { mutableIntStateOf(3) } // профиль выбран

    val tabs = remember {
        listOf(
            NavTab(R.drawable.home, "Home"),
            NavTab(R.drawable.favorite, "Favorite"),
            NavTab(R.drawable.orders, "Orders"),
            NavTab(R.drawable.profile, "Profile")
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // ===== Header =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Профиль",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                // Кнопка "камера/редактировать" справа (просто иконка из ресурсов)
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Accent)
                        .clickable { /* TODO */ },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit), // положи иконку камеры
                        contentDescription = "Edit photo",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ===== Avatar =====
            Image(
                painter = painterResource(id = R.drawable.profile_avatar), // аватар из ресурсов
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Emmanuel Oyiboke",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(Modifier.height(14.dp))

            // ===== Barcode (просто картинка) =====
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Block),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.barcode), // штрихкод картинка из ресурсов
                    contentDescription = "Barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .height(26.dp),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(Modifier.height(18.dp))

            // ===== Fields (как на макете) =====
            ProfileField(label = "Имя", value = "Emmanuel")
            Spacer(Modifier.height(12.dp))
            ProfileField(label = "Фамилия", value = "Oyiboke")
            Spacer(Modifier.height(12.dp))
            ProfileField(label = "Адрес", value = "Nigeria")
            Spacer(Modifier.height(12.dp))
            ProfileField(label = "Телефон", value = "+7 (999) 123-45-67")

            Spacer(Modifier.height(24.dp))
        }

        // ===== Bottom bar =====
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
private fun ProfileField(
    label: String,
    value: String
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false, // чисто макет (не редактируем)
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Block,
                disabledTextColor = SubTextDark,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview()
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}
