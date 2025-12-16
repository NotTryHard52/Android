package com.example.chirkov_android.ui.components


import com.example.chirkov_android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chirkov_android.ui.theme.Accent
import com.example.chirkov_android.ui.theme.Background


@Composable
fun CircularDot(
    isSelected: Boolean = false,
    onClick: () -> Unit = {}, // добавили callback
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(18.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) Accent else Background)
            .clickable { onClick() }, // клик здесь!
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
    Column {
        CircularDot()
        CircularDot(isSelected = true)
    }
}
