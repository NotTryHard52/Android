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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpracapp.ui.components.MessageDialog
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.viewModel.ForgotPasswordViewModel
import com.example.chirkov_android.ui.viewModel.ForgotState

@Composable
fun ForgotPassword(onBackClick: () -> Unit = {}, onOtpClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    val forgotPasswordViewModel = remember { ForgotPasswordViewModel() }
    var email by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }
    val forgotState by forgotPasswordViewModel.forgotState.collectAsState()
    val isLoading by forgotPasswordViewModel.isLoading.collectAsState()

    fun isEmailValid(email: String): Boolean {
        val regex = Regex("""^[a-z0-9]+@[a-z0-9]+\.[a-z]{2,}$""")
        return regex.matches(email.trim().lowercase())
    }


    LaunchedEffect(forgotState) {
        when (forgotState) {
            is ForgotState.Success -> {
                onOtpClick()
            }
            is ForgotState.Error -> {
                // ✅ Показываем диалог ошибки из ViewModel
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.ForgotPassword),
                fontSize = 32.sp
            )
            Text(
                text = stringResource(R.string.UserRestore),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                fontSize = 16.sp,
                lineHeight = 16.sp,
                color = SubTextDark
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.Email),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
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

            Spacer(modifier = Modifier.height(40.dp))

            ActiveButton(
                onClick = {
                    if (!isEmailValid(email)) {
                        forgotPasswordViewModel.showError("Некорректный email")
                        return@ActiveButton
                    }
                    forgotPasswordViewModel.sendRecoveryCode(email)
                },
                text = stringResource(R.string.Send),
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    when (forgotState) {
        is ForgotState.Success -> {
            MessageDialog(
                title = "Проверьте Ваш Email",
                description = "Мы отправили код восстановления пароля на вашу электронную почту.",
                onOk = {
                    forgotPasswordViewModel.resetState()
                    onOtpClick()
                }
            )
        }
        is ForgotState.Error -> {
            MessageDialog(
                title = "Ошибка",
                description = (forgotState as ForgotState.Error).message,
                onOk = { forgotPasswordViewModel.resetState() }
            )
        }
        else -> {}
    }
}

@Preview
@Composable
private fun ForgotPasswordPreview() {
    ForgotPassword()
}
