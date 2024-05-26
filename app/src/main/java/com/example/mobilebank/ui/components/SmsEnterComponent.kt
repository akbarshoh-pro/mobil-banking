package com.example.mobilebank.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilebank.R
import com.example.mobilebank.ui.theme.colorInputBg

@Composable
fun CodeTextField(
    value: String,
    length: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Box {

                CompositionLocalProvider(
                    LocalTextSelectionColors.provides(
                        TextSelectionColors(
                            Color.Transparent,
                            Color.Transparent
                        )
                    )
                ) {
                    Box(modifier = Modifier.drawWithContent { }) {
                        innerTextField()
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    repeat(length) { index ->

                        val currentChar = value.getOrNull(index)
                        Box(
                            modifier = modifier
                                .padding(horizontal = 4.dp)
                                .weight(1/length.toFloat())
                                .height(50.dp)
                                .clip(shape = RoundedCornerShape(16.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (currentChar != null) Color.White else colorInputBg,
                                )
                                .background(colorInputBg)
                                .align(Alignment.CenterVertically),
                        ) {
                            if (currentChar != null) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = currentChar.toString(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 22.sp,
                                    fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                                    fontWeight = FontWeight(600)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun Prev() {
    CodeTextField(
        value = "",
        length = 6,
        modifier = Modifier
            .padding(top = 16.dp, start = 4.dp, end = 4.dp)
    ) {

    }
}