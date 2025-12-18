package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
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
    var activeTab by remember { mutableIntStateOf(3) }

    val tabs = remember {
        listOf(
            NavTab(R.drawable.home, "Home"),
            NavTab(R.drawable.favorite, "Favorite"),
            NavTab(R.drawable.orders, "Orders"),
            NavTab(R.drawable.profile, "Profile")
        )
    }

    // режимы
    var isEditMode by remember { mutableStateOf(false) }

    // поля (пока просто локально; потом можно связать с ViewModel)
    var firstName by remember { mutableStateOf("Emmanuel") }
    var lastName by remember { mutableStateOf("Oyiboke") }
    var address by remember { mutableStateOf("Nigeria") }
    var phone by remember { mutableStateOf("+7 811-732-5298") }

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

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Accent)
                        .clickable {
                            isEditMode = !isEditMode
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // ===== Avatar =====
            Image(
                painter = painterResource(id = R.drawable.profile_avatar),
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

            if (isEditMode) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Изменить фото профиля",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = SubTextDark
                )
            }

            Spacer(Modifier.height(14.dp))

            // ===== Barcode (только в режиме просмотра) =====
            if (!isEditMode) {
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
                        painter = painterResource(id = R.drawable.barcode),
                        contentDescription = "Barcode",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .height(26.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(Modifier.height(18.dp))
            } else {
                Spacer(Modifier.height(10.dp))
            }

            // ===== Fields =====
            EditableProfileField(
                label = "Имя",
                value = firstName,
                enabled = isEditMode,
                onValueChange = { firstName = it },
                showCheck = isEditMode
            )
            Spacer(Modifier.height(12.dp))

            EditableProfileField(
                label = "Фамилия",
                value = lastName,
                enabled = isEditMode,
                onValueChange = { lastName = it },
                showCheck = isEditMode
            )
            Spacer(Modifier.height(12.dp))

            EditableProfileField(
                label = "Адрес",
                value = address,
                enabled = isEditMode,
                onValueChange = { address = it },
                showCheck = isEditMode
            )
            Spacer(Modifier.height(12.dp))

            EditableProfileField(
                label = "Телефон",
                value = phone,
                enabled = isEditMode,
                onValueChange = { phone = it },
                showCheck = isEditMode
            )

            // ===== Save button (только в Edit) =====
            if (isEditMode) {
                Spacer(Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Accent)
                        .clickable {
                            // TODO: сохранить (в ViewModel/сервер)
                            isEditMode = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Сохранить",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }

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
private fun EditableProfileField(
    label: String,
    value: String,
    enabled: Boolean,
    showCheck: Boolean,
    onValueChange: (String) -> Unit
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
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            trailingIcon = {
                if (showCheck) {
                    Image(
                        painter = painterResource(id = R.drawable.check), // добавь галочку
                        contentDescription = "Ok",
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Block,
                unfocusedContainerColor = Block,
                disabledContainerColor = Block,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = SubTextDark,
                disabledTextColor = SubTextDark
            )
        )
    }
}

@Preview()
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}
