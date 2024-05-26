package com.example.mobilebank.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilebank.R

@Composable
fun ClickableTextWithCircleEffect(
    text: String,
    modifier: Modifier = Modifier,
    circleSize: Dp = 64.dp,
    shape: CornerBasedShape = RoundedCornerShape(128.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(circleSize)
            .clip(shape)
            .background(
                color = Color.Transparent,
                shape = shape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 26.sp,
            fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}