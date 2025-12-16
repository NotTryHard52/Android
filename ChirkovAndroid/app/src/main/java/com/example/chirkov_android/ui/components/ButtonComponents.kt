package com.example.chirkov_android.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Disable

@Composable
fun BaseButtonContent(
    text: String,
    backgroundColor: Color = Color(0xFF2B6B8B),
    onClick: () -> Unit,
    enabled: Boolean = true, // добавьте этот параметр!
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor)
            .clickable(enabled = enabled) { // используйте enabled!
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ActiveButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    BaseButtonContent(
        text = text,
        backgroundColor = if (enabled) Accent else Disable,
        onClick = onClick,
        enabled = enabled, // передайте enabled!
        modifier = modifier.clip(RoundedCornerShape(14.dp))
    )
}

@Composable
fun DisabledButton(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    BaseButtonContent(
        text = text,
        backgroundColor = Disable,
        onClick = {},
        modifier = modifier.clip(RoundedCornerShape(14.dp))
    )
}