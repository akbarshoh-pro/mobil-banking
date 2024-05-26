package com.example.mobilebank.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CirclePin(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFF3F4FA)
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .background(color = color, shape = RoundedCornerShape(56.dp))
    )
}


