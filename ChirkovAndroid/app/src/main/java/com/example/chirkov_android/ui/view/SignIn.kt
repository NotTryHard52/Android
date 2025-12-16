package com.example.chirkov_android.ui.view

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.ActiveButton
import com.example.chirkov_android.ui.components.CircularDot
import com.example.chirkov_android.ui.components.DisabledButton
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.Hint
import com.example.chirkov_android.ui.theme.SubTextDark

@Composable
fun SignIn(modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.Hi),
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

            var email by remember { mutableStateOf("") }
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

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                Text(
                    text = stringResource(R.string.Restore),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.End,
                    color = SubTextDark
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            ActiveButton(
                onClick = {},
                text = stringResource(R.string.SignIn),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            )
        }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.CreateUser),
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

@Preview
@Composable
private fun SignInPreview() {
    SignIn()
}
