package com.example.mobilebank.presentation.screens.main.pages.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.colorInputBg
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.utils.CardNumberVisualTransformation

object PaymentScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.payment)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_wallet))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
    @Composable
    override fun Content() {
        PaymentContent()
    }
}

@Composable
private fun PaymentContent() {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            CustomTextView(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                text = stringResource(id = R.string.payment),
                fontSize = 28
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 56.dp, bottom = 56.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = colorInputBg, shape = RoundedCornerShape(16.dp))
                ) {
                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .width(280.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = CardNumberVisualTransformation(),
                        keyboardActions = KeyboardActions.Default,
                        cursorBrush = SolidColor(selectedItemColor),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                            fontWeight = FontWeight(600),
                        )
                    )
                }

            }

            item {

            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp)
                ) {
                    CustomTextView(
                        text = stringResource(id = R.string.templates),
                        fontSize = 20
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 12.dp)
                    ) {
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = colorInputBg,
                                            shape = RoundedCornerShape(100)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CustomImageView(
                                        modifier = Modifier
                                            .rotate(45F)
                                            .size(28.dp),
                                        src = R.drawable.ic_operations_closecircle
                                    )
                                }
                                CustomTextView(
                                    modifier = Modifier
                                        .padding(top = 6.dp),
                                    text = stringResource(id = R.string.add)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .fillMaxHeight()
                            .weight(0.5F),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFDCF4)),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(100.dp),
                                src = R.drawable.ic_self_transfer_to_card
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .align(Alignment.CenterStart)
                                    .width(84.dp),
                                text = stringResource(id = R.string.to_my_card),
                                fontSize = 14,
                                textAlign = TextAlign.Left,
                                lineHeight = 17F
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxHeight()
                            .weight(0.5F),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF90FFE2)),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(120.dp),
                                src = R.drawable.ic_self_transfer_to_wallet
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .align(Alignment.CenterStart)
                                    .width(84.dp),
                                text = stringResource(id = R.string.to_paynet_card),
                                textAlign = TextAlign.Left,
                                fontSize = 14,
                                lineHeight = 17F
                            )
                        }
                    }
                }
            }

        }
    }
}