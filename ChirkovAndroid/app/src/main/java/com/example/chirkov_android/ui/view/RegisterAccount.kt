package com.example.chirkov_android.ui.view

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.chirkov_android.data.module.SignUpRequest
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.myfirstapplication.ui.viewModel.SignUpState
import com.example.myfirstapplication.ui.viewModel.SignUpViewModel

@Composable
fun RegisterAccount(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val signUpViewModel = remember { SignUpViewModel() } // без Hilt
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signUpState by signUpViewModel.signUpState.collectAsState()

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
                Toast.makeText(context, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
            }
            is SignUpState.Error -> {
                Toast.makeText(context, (signUpState as SignUpState.Error).message, Toast.LENGTH_LONG).show()
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
                .padding(top = 67.dp),
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
                .padding(top = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.Registry),
                fontSize = 32.sp
            )
            Text(
                text = stringResource(R.string.Data),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = SubTextDark
            )
        }

        Spacer(modifier = Modifier.height(54.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            // Email поле
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
            }

            // Email поле
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

            // Password поле + checkbox
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
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Активная кнопка регистрации
            ActiveButton(
                text = stringResource(R.string.SignUp),
                onClick = {
                    val signUpRequest = SignUpRequest(
                        email = email,
                        password = password
                    )
                    signUpViewModel.signUp(signUpRequest)
                },
                modifier = Modifier.fillMaxWidth()
            )

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
