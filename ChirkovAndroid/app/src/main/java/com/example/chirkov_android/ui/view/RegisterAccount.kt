package com.example.chirkov_android.ui.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.ui.components.CircularDot
import com.example.chirkov_android.ui.components.DisabledButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Hint
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.R

@Composable
fun RegisterAccount(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.Registry),
                modifier = Modifier.padding(top = 78.dp),
                fontSize = 32.sp
            )
            Text(
                text = stringResource(R.string.Data),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = SubTextDark
            )
        }

        Spacer(modifier = Modifier.height(54.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var name by remember { mutableStateOf("") }
            Text(
                text = stringResource(R.string.Name),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
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

            var email by remember { mutableStateOf("") }
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

            var password by remember { mutableStateOf("") }
            Text(
                text = stringResource(R.string.Password),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background,
                    unfocusedContainerColor = Background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.union),
                        contentDescription = "Глаз",
                        tint = LocalContentColor.current
                    )
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularDot()
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.PersonalData),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Hint,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DisabledButton(text = stringResource(R.string.SignUp),)

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.UserSignIn),
                modifier = Modifier
                    .padding(bottom = 47.dp)
                    .fillMaxWidth(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun RegisterAccountPreview() {
    RegisterAccount()
}
