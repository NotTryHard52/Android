package com.example.chirkov_android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpracapp.ui.components.MessageDialog
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.SubTextDark

@Composable
fun CreateNewPassword(
    onBackClick: () -> Unit = {},
    onSaveClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showMismatchError by remember { mutableStateOf(false) }
    var showEmptyError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 67.dp)
                .clickable { onBackClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = null,
                modifier = Modifier.size(44.dp)
            )
        }

        // заголовок
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.NewPasswoes),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.NextPassword),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = SubTextDark,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(38.dp))

        // поле Пароль
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(R.string.Password),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background,
                    unfocusedContainerColor = Background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible)
                                    R.drawable.union_on
                                else
                                    R.drawable.union
                            ),
                            contentDescription = if (passwordVisible)
                                "Скрыть пароль"
                            else
                                "Показать пароль",
                            tint = LocalContentColor.current
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // поле Подтверждение пароля
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(R.string.VerifyPassword),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = if (confirmPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background,
                    unfocusedContainerColor = Background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (confirmPasswordVisible)
                                    R.drawable.union_on
                                else
                                    R.drawable.union
                            ),
                            contentDescription = if (confirmPasswordVisible)
                                "Скрыть пароль"
                            else
                                "Показать пароль",
                            tint = LocalContentColor.current
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        ActiveButton(
            text = stringResource(R.string.Save),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                passwordError = false
                confirmPasswordError = false

                when {
                    password.isBlank() || confirmPassword.isBlank() -> {
                        showEmptyError = true
                    }
                    password != confirmPassword -> {
                        showMismatchError = true
                        passwordError = true
                        confirmPasswordError = true
                    }
                    else -> {
                        onSaveClick(password)
                    }
                }
            }
        )
    }

    if (showEmptyError) {
        MessageDialog(
            title = "Поля не заполнены",
            description = "Введите новый пароль и его подтверждение",
            onOk = { showEmptyError = false }
        )
    }

    if (showMismatchError) {
        MessageDialog(
            title = "Пароли не совпадают",
            description = "Повторите ввод пароля",
            onOk = { showMismatchError = false }
        )
    }
}

@Preview
@Composable
private fun CreateNewPasswordPreview() {
    CreateNewPassword()
}