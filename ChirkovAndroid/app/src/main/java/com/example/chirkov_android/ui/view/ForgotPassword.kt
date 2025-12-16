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

@Composable
fun ForgotPassword(onBackClick: () -> Unit = {}, onOtpClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }

    fun isEmailValid(email: String): Boolean {
        val regex = Regex("""^[a-z0-9]+@[a-z0-9]+\.[a-z]{2,}$""")
        return regex.matches(email.trim().lowercase())
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
            }

            Spacer(modifier = Modifier.height(40.dp))
        ActiveButton(
            onClick = {
                if (!isEmailValid(email)) {
                    showEmailError = true
                    return@ActiveButton
                }
                showSuccessDialog = true // ✅ успех
            },
            text = stringResource(R.string.Send),
            modifier = Modifier.fillMaxWidth() // ✅ убрали .clip()
        )
        }
    if (showEmailError) {
        MessageDialog(
            title = "Некорректный email",
            description = "Email должен соответствовать формату name@domenname.ru\n(только маленькие буквы и цифры, домен минимум 3 символа)",
            onOk = { showEmailError = false }
        )
    }

    if (showSuccessDialog) {
        MessageDialog(
            title = "Проверьте Ваш Email",
            description = "Мы отправили код восстановления пароля на вашу электронную почту.",
            onOk = {
                showSuccessDialog = false
                onOtpClick()
            }
        )
    }
    }

@Preview
@Composable
private fun ForgotPasswordPreview() {
    ForgotPassword()
}
