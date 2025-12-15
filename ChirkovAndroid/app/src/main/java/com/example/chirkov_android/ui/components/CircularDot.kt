package com.example.chirkov_android.ui.components


import com.example.chirkov_android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chirkov_android.ui.theme.Background


@Composable
fun CircularDot(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(18.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.iconamoon_shield_yes_fill),
            contentDescription = null,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Preview
@Composable
private fun CircularDotPreview() {
    CircularDot()
}
