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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.viewModel.CreateNewPasswordViewModel

@Composable
fun CreateNewPassword(
    onBackClick: () -> Unit = {},
    onNavigateToSignIn: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: CreateNewPasswordViewModel = remember { CreateNewPasswordViewModel() }
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showMismatchError by remember { mutableStateOf(false) }
    var showEmptyError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    if (isSuccess) {
        MessageDialog(
            title = "Пароль изменён",
            description = "Теперь вы можете войти с новым паролем.",
            onOk = {
                viewModel.resetState()
                onNavigateToSignIn()
            }
        )
    }

    if (isError != null) {
        MessageDialog(
            title = "Ошибка",
            description = isError ?: "",
            onOk = { viewModel.resetState() }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Background)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier.size(16.dp)
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
                style = CustomTheme.typography.HeadingRegular32,
                color = CustomTheme.colors.text,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.NextPassword),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                style = CustomTheme.typography.BodyRegular16,
                color = CustomTheme.colors.subTextDark,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(38.dp))

        // поле Пароль
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(R.string.Password),
                style = CustomTheme.typography.BodyMedium16,
                color = CustomTheme.colors.text,
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
                style = CustomTheme.typography.BodyMedium16,
                color = CustomTheme.colors.text,
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
            enabled = !isLoading,
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
                        viewModel.updatePassword(password)
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