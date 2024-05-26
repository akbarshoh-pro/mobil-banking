package com.example.mobilebank.presentation.screens.what_is

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.primaryText
import com.example.mobilebank.ui.theme.secondaryText
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.utils.getCurrentLanguage
import org.orbitmvi.orbit.compose.collectAsState

class WhatIsThisScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: WhatIsContract.ViewModel = getViewModel<WhatIsViewModel>()
        WhatIsContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
fun WhatIsContent(
    uiState: State<WhatIsContract.UIState>,
    onEventDispatcher: (WhatIsContract.Intent) -> Unit
) {
    CustomAppBar(
        title = "",
        onClick = { onEventDispatcher(WhatIsContract.Intent.BackHomeScreen) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 96.dp)
                    .fillMaxSize()
            ) {
                item {
                    Column {
                        CustomImageView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                            src = if (getCurrentLanguage() == "ru") R.drawable.paynet_card_info_ru else R.drawable.paynet_card_info_uz
                        )

                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 24.dp),
                            text = stringResource(id = R.string.what_is_paynet_card),
                            fontSize = 24,
                            fontWeight = 600
                        )
                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, end = 36.dp),
                            text = stringResource(id = R.string.transfer_with_out_commission),
                            fontSize = 20,
                            fontWeight = 400,
                            textAlign = TextAlign.Left
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 24.dp)
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(28.dp),
                                src = R.drawable.ic_operations_top_up_wallet
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 42.dp)
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string._0_percentage_for_fill),
                                    fontSize = 18,
                                    fontWeight = 600,
                                    color = primaryText
                                )
                                CustomTextView(
                                    text = stringResource(id = R.string.offer_for_fill),
                                    textAlign = TextAlign.Left,
                                    fontSize = 14,
                                    fontWeight = 500,
                                    color = secondaryText
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(28.dp),
                                src = R.drawable.ic_action_transfers
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 42.dp)
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string._0_percentage_for_transfer),
                                    fontSize = 18,
                                    fontWeight = 600,
                                    color = primaryText
                                )
                                CustomTextView(
                                    text = stringResource(id = R.string.to_other_paynet_cards),
                                    textAlign = TextAlign.Left,
                                    fontSize = 14,
                                    fontWeight = 500,
                                    color = secondaryText
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(28.dp),
                                src = R.drawable.ic_operations_money
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 42.dp)
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.cash_withdrawal),
                                    fontSize = 18,
                                    fontWeight = 600,
                                    color = primaryText
                                )
                                CustomTextView(
                                    text = stringResource(id = R.string.in_paynet_agent),
                                    textAlign = TextAlign.Left,
                                    fontSize = 14,
                                    fontWeight = 500,
                                    color = secondaryText
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(28.dp),
                                src = R.drawable.ic_operations_withdrawal_wallet
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 42.dp)
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.card_payments),
                                    fontSize = 18,
                                    fontWeight = 600,
                                    color = primaryText
                                )
                                CustomTextView(
                                    text = stringResource(id = R.string.card_payment_options),
                                    textAlign = TextAlign.Left,
                                    fontSize = 14,
                                    fontWeight = 500,
                                    color = secondaryText
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 16.dp, bottom = 8.dp)
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(28.dp),
                                src = R.drawable.ic_operations_cashback_2
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 42.dp)
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.cashback_to_2_2),
                                    fontSize = 18,
                                    fontWeight = 600,
                                    color = primaryText
                                )
                                CustomTextView(
                                    text = stringResource(id = R.string.fill_cashback_after_transfer),
                                    textAlign = TextAlign.Left,
                                    fontSize = 14,
                                    fontWeight = 500,
                                    color = secondaryText
                                )
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
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp, top = 24.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = btnNextDisable,
                        disabledContentColor = textNextDisable,
                        contentColor = textNextEnable,
                        containerColor = btnNextEnable
                    ),
                    onClick = {  },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    CustomTextView(
                        text = stringResource(id = R.string.know_contract),
                        fontSize = 18,
                        fontWeight = 700,
                        color = Color.White
                    )
                }
            }

        }
    }
}





























