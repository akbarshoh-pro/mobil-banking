package com.example.mobilebank.presentation.dialog

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.data.model.MarkerData
import com.example.mobilebank.ui.components.CustomTextView

class LocationDialog(
    private val data: MarkerData
) : Screen {
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        LocationContent(data = data) {
            bottomSheetNavigator.hide()
        }
    }

    @Composable
    private fun LocationContent(data: MarkerData, clickCancel: () -> Unit) {
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
                    Image(
                        modifier = Modifier
                            .padding(top = 42.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(250.dp),
                        painter = painterResource(id = R.drawable.img_location),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(28.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp, end = 16.dp),
                            text = data.location,
                            textAlign = TextAlign.Left,
                            fontSize = 14
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.material3.Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(24.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_action_phone_alt),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = data.phone
                        )
                    }
                }
            }
        }
    }
}