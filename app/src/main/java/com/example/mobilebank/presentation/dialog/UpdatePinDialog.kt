package com.example.mobilebank.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.selectedItemColor

class UpdatePinDialog(val onClick: () -> Unit = {}) : Screen {
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        UpdatePinContent(onClick = onClick) {
            bottomSheetNavigator.hide()
        }
    }
    @Composable
    fun UpdatePinContent(onClick: () -> Unit = {}, clickCancel: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.TopEnd)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) { clickCancel() }
                        .background(color = Color(0xFFD6D5D5), shape = RoundedCornerShape(100))
                        .size(32.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(16.dp),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color(0xFF616060)
                    )
                }
                Column {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                            .width(30.dp)
                            .height(4.dp)
                            .background(Color(0xFFADADAD), shape = RoundedCornerShape(4.dp))
                    )
                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 16.dp, top = 36.dp),
                        text = stringResource(id = R.string.forget_pin),
                        fontSize = 18
                    )
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, end = 24.dp),
                        text = stringResource(id = R.string.for_update_pin),
                        textAlign = TextAlign.Left,
                        fontSize = 14,
                        color = Color(0xFF6D6C6C)
                    )

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 16.dp)
                            .height(48.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(selectedItemColor),
                        elevation = ButtonDefaults.buttonElevation(2.dp),
                        onClick = onClick
                    ) {
                        CustomTextView(
                            text = stringResource(id = R.string.log_out_pin),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

