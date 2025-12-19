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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidpracapp.ui.components.MessageDialog
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.CustomTheme
import com.example.chirkov_android.ui.theme.Raleway
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.viewModel.SignInState
import com.example.chirkov_android.ui.viewModel.SignInViewModel

@Composable
fun SignIn(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSuccessNavigate: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    vm: SignInViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var showEmailError by remember { mutableStateOf(false) }
    var showEmptyFieldsError by remember { mutableStateOf(false) }

    val signInState by vm.signInState.collectAsState()

    fun isEmailValid(v: String): Boolean {
        val regex = Regex("""^[a-z0-9]+@[a-z0-9]+\.[a-z]{2,}$""")
        return regex.matches(v.trim().lowercase())
    }

    fun areFieldsEmpty(): Boolean = email.trim().isEmpty() || password.trim().isEmpty()

    LaunchedEffect(signInState) {
        if (signInState is SignInState.Success) {
            onSuccessNavigate()
            vm.resetState()
        }
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.Hi),
                style = CustomTheme.typography.HeadingRegular32,
                color = CustomTheme.colors.text)
            Text(
                text = stringResource(R.string.Data),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                style = CustomTheme.typography.BodyRegular16,
                color = CustomTheme.colors.subTextDark,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(54.dp))

        Column(modifier = Modifier.fillMaxWidth()) {

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

            Spacer(modifier = Modifier.height(18.dp))

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
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                                    id = if (passwordVisible) R.drawable.union_on else R.drawable.union
                                ),
                                contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                                tint = LocalContentColor.current
                            )
                        }
                    }
                )

                Text(
                    text = stringResource(R.string.Restore),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onForgotPasswordClick() },
                    style = CustomTheme.typography.BodyRegular12,
                    color = CustomTheme.colors.subTextDark,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val isLoading = signInState is SignInState.Loading

            ActiveButton(
                onClick = {
                    if (areFieldsEmpty()) {
                        showEmptyFieldsError = true
                        return@ActiveButton
                    }
                    if (!isEmailValid(email)) {
                        showEmailError = true
                        return@ActiveButton
                    }
                    vm.signIn(email = email, password = password)
                },
                text = if (isLoading) "..." else stringResource(R.string.SignIn),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(bottom = 47.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.SignInOne),
                style = CustomTheme.typography.BodyRegular16,
                color = CustomTheme.colors.subTextDark,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.CreateUser),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }

    if (signInState is SignInState.Error) {
        MessageDialog(
            title = "Ошибка входа",
            description = (signInState as SignInState.Error).message,
            onOk = { vm.resetState() }
        )
    }

    if (showEmptyFieldsError) {
        MessageDialog(
            title = "Поля не заполнены",
            description = "Заполните email и пароль для входа",
            onOk = { showEmptyFieldsError = false }
        )
    }

    if (showEmailError) {
        MessageDialog(
            title = "Некорректный email",
            description = "Email должен соответствовать формату name@domenname.ru\n(только маленькие буквы и цифры, домен минимум 3 символа)",
            onOk = { showEmailError = false }
        )
    }
}

@Preview
@Composable
private fun SignInPreview() {
    SignIn()
}
