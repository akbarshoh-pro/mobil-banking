package com.example.mobilebank.presentation.screens.card_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.mobilebank.R
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.presentation.dialog.AddCardDialog
import com.example.mobilebank.presentation.dialog.DeleteCardDialog
import com.example.mobilebank.presentation.screens.main.pages.home.HomeContract
import com.example.mobilebank.presentation.screens.main.pages.payment.PaymentScreen
import com.example.mobilebank.presentation.screens.main.pages.transfer.TransferScreen
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.bgWithCards
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.utils.getCardType
import com.example.mobilebank.utils.getGradient
import com.example.mobilebank.utils.hideCardNumbers
import com.example.mobilebank.utils.moneyFormat
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class CardDetailsScreen(
    private val data: CardData
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: CardDetailsContract.ViewModel = getViewModel<CardDetailsViewModel>()
//        val tabNavigator = LocalTabNavigator.current
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                CardDetailsContract.SideEffect.NavigateToPaymentPage -> {
//                    tabNavigator.current = PaymentScreen
                }
                CardDetailsContract.SideEffect.NavigateToTransferPage -> {
//                    tabNavigator.current = TransferScreen
                }
                is CardDetailsContract.SideEffect.OpenDeleteCardDialog -> {
                    bottomSheetNavigator.show(
                        DeleteCardDialog(
                            delete = {
                                viewModel.onEventDispatcher(CardDetailsContract.Intent.DeleteCard(sideEffect.id))
                            },
                            dismiss = {
                                bottomSheetNavigator.hide()
                            }
                        )
                    )
                }
            }
        }

        CardDetailsContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher,
            data = data
        )
    }
}

@Composable
private fun CardDetailsContent(
    uiState: State<CardDetailsContract.UIState>,
    onEventDispatcher: (CardDetailsContract.Intent) -> Unit,
    data: CardData
) {
    Box(
        modifier = Modifier
            .background(bgWithCards)
            .fillMaxSize()
    ) {
        CustomAppBar(
            title = if (getCardType(data.pan) == R.drawable.ic_paymentsystem_humo) "Humo" else "Uzcard",
            onClick = { onEventDispatcher(CardDetailsContract.Intent.BackToHomeScreen)  }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .height(220.dp)
                            .background(
                                brush = getGradient(data.themeType),
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
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(0.3F)
                                .height(80.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_plus),
                                        contentDescription = null,
                                        tint = selectedItemColor
                                    )

                                    CustomTextView(
                                        modifier = Modifier
                                            .padding(top = 8.dp),
                                        text = stringResource(id = R.string.add_m),
                                        fontSize = 14
                                    )
                                }
                            }
                        }

                        Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(0.3F)
                                .height(80.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = null
                                ) { onEventDispatcher(CardDetailsContract.Intent.NavigateToTransferPage) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_transfer),
                                        contentDescription = null,
                                        tint = selectedItemColor
                                    )

                                    CustomTextView(
                                        modifier = Modifier
                                            .padding(top = 8.dp),
                                        text = stringResource(id = R.string.transfer_m),
                                        fontSize = 14
                                    )
                                }
                            }
                        }

                        Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(0.3F)
                                .height(80.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = null
                                ) { onEventDispatcher(CardDetailsContract.Intent.NavigateToPaymentPage) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_wallet),
                                        contentDescription = null,
                                        tint = selectedItemColor
                                    )

                                    CustomTextView(
                                        modifier = Modifier
                                            .padding(top = 8.dp),
                                        text = stringResource(id = R.string.payment_m),
                                        fontSize = 14
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 10.dp),
                            text = stringResource(id = R.string.card_settings),
                            fontSize = 18
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 12.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onEventDispatcher(CardDetailsContract.Intent.NavigateToCardTheme(data)) }
                                .fillMaxWidth()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                src = R.drawable.ic_actions_edit
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 46.dp),
                                text = stringResource(id = R.string.style_and_name)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 16.dp, bottom = 12.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onEventDispatcher(CardDetailsContract.Intent.OpenDeleteCardDialog(data.id)) }
                                .fillMaxWidth()
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 2.dp)
                                    .size(24.dp),
                                tint = Color(0xFFEB4438),
                                painter = painterResource(id = R.drawable.ic_action_trash),
                                contentDescription = null
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 46.dp),
                                text = stringResource(id = R.string.delete_card),
                                color = Color(0xFFEB4438)
                            )
                        }
                    }
                }
            }
        }
    }
}