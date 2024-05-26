package com.example.mobilebank.presentation.screens.card_theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomButton
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.colorInputBg
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.ui.theme.unSelectedItemColor
import com.example.mobilebank.utils.getCardType
import com.example.mobilebank.utils.getGradient
import com.example.mobilebank.utils.moneyFormat

class CardThemeScreen(
    private val data: CardData
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: CardThemeContract.ViewModel = getViewModel<CardThemeViewModel>()
        CardThemeContent(
            onEventDispatcher = viewModel::onEventDispatcher,
            data = data
        )
    }
}

@Composable
fun CardThemeContent(
    onEventDispatcher: (CardThemeContract.Intent) -> Unit,
    data: CardData
) {
    var name by remember { mutableStateOf(data.name) }
    var selectedItem by remember { mutableStateOf(0) }
    var selectedColor by remember { mutableStateOf(getGradient(0)) }

    BackHandler {
        onEventDispatcher(CardThemeContract.Intent.BackCardDetails(data))
    }

    CustomAppBar(
        title = stringResource(id = R.string.card_style),
        onClick = { onEventDispatcher(CardThemeContract.Intent.BackCardDetails(data)) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .height(220.dp)
                        .background(
                            brush = selectedColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                ) {

                    if (getCardType(data.pan) == R.drawable.ic_paymentsystem_humo) {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .width(160.dp)
                                .height(50.dp),
                            src = getCardType(data.pan)
                        )
                    }
                    else {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 24.dp)
                                .size(90.dp),
                            src = getCardType(data.pan)
                        )
                    }

                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 16.dp, start = 16.dp),
                        text = data.name,
                        color = Color.White,
                        fontSize = 20
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    ) {
                        CustomTextView(
                            text = moneyFormat(data.amount.toString()),
                            fontSize = 28,
                            color = Color.White,
                            fontWeight = 700
                        )
                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            text = stringResource(id = R.string.money),
                            fontSize = 24,
                            color = Color(0x9CFFFFFF),
                            fontWeight = 600
                        )
                    }

                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 56.dp, start = 16.dp),
                        text = "**** **** **** ${data.pan}",
                        color = Color.White,
                        fontSize = 16,
                        fontWeight = 20
                    )

                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(top = 56.dp, end = 16.dp),
                        text = if (data.expiredMonth < 10) "0${data.expiredMonth}/${data.expiredYear.toString().substring(2)}" else "${data.expiredMonth}/${data.expiredYear.toString().substring(2)}",
                        color = Color.White,
                        fontSize = 16,
                        fontWeight = 20
                    )

                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 36.dp),
                    colors = CardDefaults
                        .cardColors(Color.White),
                    elevation = CardDefaults
                        .elevatedCardElevation(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(color = colorInputBg, shape = RoundedCornerShape(12.dp))
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp, top = 16.dp)
                                .width(280.dp),
                            value = name,
                            onValueChange = { value ->
                                if (value.length <= 15) {
                                    name = value
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions.Default,
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(600)
                            )
                        )

                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 4.dp),
                            text = stringResource(id = R.string.card_name),
                            color = unSelectedItemColor,
                            fontSize = 14,
                            fontWeight = 600,
                        )
                    }

                    CustomTextView(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .padding(start = 18.dp),
                        text = stringResource(id = R.string.limit_symbols),
                        color = unSelectedItemColor
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 36.dp),
                    colors = CardDefaults
                        .cardColors(Color.White),
                    elevation = CardDefaults
                        .elevatedCardElevation(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .align(Alignment.Center),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            items(9) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 0.dp)
                                        .size(56.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(100),
                                        )
                                        .border(
                                            border = BorderStroke(
                                                2.5.dp,
                                                if (it == selectedItem) selectedItemColor else Color.White
                                            ), shape = RoundedCornerShape(100)
                                        )
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = null
                                        ) {
                                            selectedItem = it
                                            selectedColor = getGradient(selectedItem)
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(48.dp)
                                            .background(
                                                brush = getGradient(it),
                                                shape = RoundedCornerShape(100),
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(10.dp),
            ) {
                CustomButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp, top = 24.dp),
                    text = stringResource(id = R.string.continue_btn),
                    fontSize = 20,
                    fontWeight = 700,
                    textColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    enabled = name.length >= 4,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = btnNextDisable,
                        disabledContentColor = textNextDisable,
                        contentColor = textNextEnable,
                        containerColor = btnNextEnable
                    ),
                    click = {
                        onEventDispatcher(CardThemeContract.Intent.UpdateCard(CardData(
                            id = data.id,
                            name = name,
                            amount = data.amount,
                            owner = data.owner,
                            pan = data.pan,
                            expiredMonth = data.expiredMonth,
                            expiredYear = data.expiredYear,
                            themeType = selectedItem,
                            isVisible = data.isVisible
                        )))
                    }
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    CardThemeContent(
//        data = CardData("0", "Uzcard", 10000, "Akbarshoh", "0001", 2028, 6, 3, false)
//    )
//}