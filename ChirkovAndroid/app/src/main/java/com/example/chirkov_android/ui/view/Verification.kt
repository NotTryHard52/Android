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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.chirkov_android.R
import com.example.chirkov_android.ui.components.OTP
import com.example.chirkov_android.ui.theme.Background
import com.example.chirkov_android.ui.theme.SubTextDark
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.viewModel.VerificationViewModel

@Composable
fun Verification(
    email: String,
    onBackClick: () -> Unit = {},
    onSuccess: () -> Unit = {},
    viewModel: VerificationViewModel = remember { VerificationViewModel() }
) {
    var code by remember { mutableStateOf(List(6) { "" }) }
    var secondsLeft by remember { mutableStateOf(30) }
    var canResend by remember { mutableStateOf(false) }

    val isError by viewModel.isError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1_000)
            secondsLeft--
        }
        canResend = true
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
            Text(text = stringResource(R.string.OTP), fontSize = 32.sp)
            Text(
                text = stringResource(R.string.OTPEmail),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = SubTextDark,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.OTPCode),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            code.forEachIndexed { index, value ->
                OTP(
                    value = value,
                    isError = isError,
                    onValueChange = { new ->
                        val newCode = code.toMutableList()
                        newCode[index] = new
                        code = newCode
                        viewModel.resetError()

                        if (newCode.all { it.isNotEmpty() } && !isLoading) {
                            val fullCode = newCode.joinToString("")
                            viewModel.verifyCode(
                                email = email,
                                code = fullCode,
                                onSuccess = onSuccess
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (!canResend) {
                Text(
                    text = String.format("00:%02d", secondsLeft),
                    fontSize = 12.sp,
                    color = SubTextDark
                )
            } else {
                Text(
                    text = "Отправить снова",
                    fontSize = 12.sp,
                    color = Accent,
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        // здесь дергаешь signUp/переотправку OTP на email
                        secondsLeft = 30
                        canResend = false
                        viewModel.resetError()
                        code = List(6) { "" }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun VerificationPreview() {

}