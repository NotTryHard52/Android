package com.example.chirkov_android.ui.view

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpracapp.ui.components.MessageDialog
import com.example.chirkov_android.ui.components.CircularDot
import com.example.chirkov_android.ui.components.DisabledButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Hint
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.R
import com.example.chirkov_android.data.module.SignUpRequest
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Block
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.theme.Raleway
import com.example.myfirstapplication.ui.viewModel.SignUpState
import com.example.myfirstapplication.ui.viewModel.SignUpViewModel

@Composable
fun RegisterAccount(onSignInClick: () -> Unit = {}, onOtpClick: (String) -> Unit = {}, modifier: Modifier = Modifier) {
    val signUpViewModel = remember { SignUpViewModel() }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isPersonalDataChecked by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }
    var showServerError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val signUpState by signUpViewModel.signUpState.collectAsState()

    fun isEmailValid(email: String): Boolean {
        val regex = Regex("""^[a-z0-9]+@[a-z0-9]+\.[a-z]{2,}$""")
        return regex.matches(email.trim().lowercase())
    }

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                onOtpClick(email)
            }
            is SignUpState.Error -> {
                errorMessage = when {
                    (signUpState as SignUpState.Error).message.contains("нет соединения") ||
                            (signUpState as SignUpState.Error).message.contains("network") ||
                            (signUpState as SignUpState.Error).message.contains("timeout") ->
                        "Нет соединения с интернетом\nПроверьте подключение и повторите"

                    (signUpState as SignUpState.Error).message.contains("сервер") ||
                            (signUpState as SignUpState.Error).message.contains("server") ->
                        "Ошибка сервера\nПопробуйте позже"

                    else -> (signUpState as SignUpState.Error).message
                }
                showServerError = true
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top
    ) {
        // Иконка назад
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Background)
                .clickable {  },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier.size(16.dp)
            )
        }

        // Заголовок
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.Registry),
                style = CustomTheme.typography.HeadingRegular32,
                color = CustomTheme.colors.text
            )
            Text(
                text = stringResource(R.string.Data),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                style = CustomTheme.typography.BodyRegular16,
                color = CustomTheme.colors.subTextDark
            )
        }

        Spacer(modifier = Modifier.height(54.dp))

        // Форма
        Column(modifier = Modifier.fillMaxWidth()) {
            // Поле Имя
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.Name),
                    style = CustomTheme.typography.BodyMedium16,
                    color = CustomTheme.colors.text
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Background,
                        unfocusedContainerColor = Background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Поле Email
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.Email),
                    style = CustomTheme.typography.BodyMedium16,
                    color = CustomTheme.colors.text,
                    lineHeight = 20.sp
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Background,
                        unfocusedContainerColor = Background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Поле Password + чекбокс
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
                        IconButton(onClick = { passwordVisible = !passwordVisible }) { // ✅ кликабельная иконка
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible) R.drawable.union_on else R.drawable.union
                                ),
                                contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                                tint = LocalContentColor.current
                            )
                        }
                    }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularDot(
                        isSelected = isPersonalDataChecked,
                        onClick = { isPersonalDataChecked = !isPersonalDataChecked }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.PersonalData),
                        style = CustomTheme.typography.BodyRegular16,
                        color = CustomTheme.colors.hint,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка регистрации (активна только при чекбоксе)
            ActiveButton(
                text = stringResource(R.string.SignUp),
                onClick = {
                    if (!isEmailValid(email)) {
                        showEmailError = true
                        return@ActiveButton
                    }
                    if (isPersonalDataChecked) {
                        val signUpRequest = SignUpRequest(
                            email = email,
                            password = password
                        )
                        signUpViewModel.signUp(signUpRequest)
                    }
                },
                enabled = isPersonalDataChecked,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(bottom = 47.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.UserSignIn),
                    style = CustomTheme.typography.BodyRegular16,
                    color = CustomTheme.colors.subTextDark,
                    lineHeight = 16.sp,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.SignIn),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }
        }
    }
    if (showEmailError) {
        MessageDialog(
            title = "Некорректный email",
            description = "Email должен соответствовать формату name@domenname.ru\n(только маленькие буквы и цифры, домен минимум 3 символа)",
            onOk = { showEmailError = false }
        )
    }

    if (showServerError) {
        MessageDialog(
            title = "Ошибка",
            description = errorMessage,
            onOk = {
                showServerError = false
                signUpViewModel.resetState()
            }
        )
    }
}

@Preview
@Composable
private fun RegisterAccountPreview() {
    RegisterAccount()
}
