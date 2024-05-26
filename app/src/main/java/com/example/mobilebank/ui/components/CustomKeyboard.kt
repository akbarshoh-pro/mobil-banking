package com.example.mobilebank.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.ClickableTextWithCircleEffect

@Composable
internal fun CustomKeyboard(
    modifier: Modifier = Modifier,
    block: (Int) -> Unit,
    clear: () -> Unit,
    itemModifier: Modifier = Modifier,
    boxScope: (BoxScope) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row {
            ClickableTextWithCircleEffect(text = "1") { block(1) }
            ClickableTextWithCircleEffect(
                text = "2",
                modifier = Modifier
                    .padding(horizontal = 36.dp)
            ) { block(2) }
            ClickableTextWithCircleEffect(text = "3") { block(3) }
        }

        Row(
            modifier = Modifier
                .padding(vertical = 26.dp)
        ) {
            ClickableTextWithCircleEffect(text = "4") { block(4) }

            ClickableTextWithCircleEffect(
                text = "5",
                modifier = Modifier
                    .padding(horizontal = 36.dp)
            ) { block(5) }

            ClickableTextWithCircleEffect(text = "6") { block(6) }
        }

        Row(
            modifier = Modifier
                .padding(bottom = 26.dp)
        ) {
            ClickableTextWithCircleEffect(text = "7") { block(7) }

            ClickableTextWithCircleEffect(
                text = "8",
                modifier = Modifier
                    .padding(horizontal = 36.dp)
            ) { block(8) }

            ClickableTextWithCircleEffect(text = "9") { block(9) }
        }

        Row {
            Box(
                modifier = itemModifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(128.dp))
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(128.dp)
                    ),
                contentAlignment = Alignment.Center
            ) { boxScope(this) }

            ClickableTextWithCircleEffect(
                text = "0",
                modifier = Modifier
                    .padding(horizontal = 36.dp)
            )
            { block(0) }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(128.dp))
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(128.dp)
                    )
                    .clickable(onClick = clear),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(28.dp)
                )
            }
        }
    }
}