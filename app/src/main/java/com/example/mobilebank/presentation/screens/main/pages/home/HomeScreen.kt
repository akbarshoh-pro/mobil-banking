package com.example.mobilebank.presentation.screens.main.pages.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.mobilebank.R
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.presentation.dialog.AddCardDialog
import com.example.mobilebank.presentation.screens.main.pages.payment.PaymentScreen
import com.example.mobilebank.presentation.screens.main.pages.transfer.TransferScreen
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.ui.theme.textColor
import com.example.mobilebank.ui.theme.unSelectedItemColor
import com.example.mobilebank.utils.formatPhoneNumber
import com.example.mobilebank.utils.getCardType
import com.example.mobilebank.utils.getCurrentDate
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.getGradient
import com.example.mobilebank.utils.logger
import com.example.mobilebank.utils.moneyFormat
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

object HomeScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.home)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_home))

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
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val tabNavigator = LocalTabNavigator.current
        viewModel.onEventDispatcher(HomeContract.Intent.GetAllCards)

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                HomeContract.SideEffect.OpenAddCardDialog -> {
                    bottomSheetNavigator.show(
                        AddCardDialog(
                            blockUz = {
                                bottomSheetNavigator.hide()
                                viewModel.onEventDispatcher(HomeContract.Intent.OpenAddUzbCardScreen)
                            },
                            blockRu = {
                                bottomSheetNavigator.hide()
                            },
                            blockCancel = {
                                bottomSheetNavigator.hide()
                            }
                        )
                    )
                }
                HomeContract.SideEffect.NavigateToPaymentPage -> {
                    tabNavigator.current = PaymentScreen
                }
                HomeContract.SideEffect.NavigateToTransferPage -> {
                    tabNavigator.current = TransferScreen
                }
            }
        }
        HomeContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }

    @Composable
    private fun HomeContent(
        uiState: State<HomeContract.UIState>,
        onEventDispatcher: (HomeContract.Intent) -> Unit
    ) {
        onEventDispatcher(HomeContract.Intent.GetData)
        val context = LocalContext.current
        val noRippleInteractionSource = remember { MutableInteractionSource() }
        var showBalance by remember { mutableStateOf(false) }
        var phone by remember { mutableStateOf("+998900993936") }
        var totalAmount by remember { mutableStateOf(0L) }
        var list by remember { mutableStateOf(listOf<CardData>()) }
        val url = "https://chat.paynet.uz"

        when(uiState.value) {
            is HomeContract.UIState.GetUIState -> {
                showBalance = (uiState.value as HomeContract.UIState.GetUIState).showBalance
                phone = (uiState.value as HomeContract.UIState.GetUIState).phone
            }
            is HomeContract.UIState.GetCardsData -> {
                list = (uiState.value as HomeContract.UIState.GetCardsData).ls
                totalAmount = (uiState.value as HomeContract.UIState.GetCardsData).totalAmount
            }
        }

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

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = noRippleInteractionSource,
                            indication = null
                        ) { onEventDispatcher(HomeContract.Intent.OpenProfileScreen) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomImageView(
                        modifier = Modifier
                            .size(36.dp),
                        src = R.drawable.app_logo
                    )

                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = formatPhoneNumber(phone),
                        fontWeight = 200,
                        color = textColor
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp),
                ) {

                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable(
                                interactionSource = noRippleInteractionSource,
                                indication = null
                            ) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_operator),
                        contentDescription = null,
                        tint = unSelectedItemColor
                    )

                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = noRippleInteractionSource,
                                indication = null
                            ) { onEventDispatcher(HomeContract.Intent.OpenNotificationScreen) },
                        painter = painterResource(id = R.drawable.ic_uvedom),
                        contentDescription = null,
                        tint = unSelectedItemColor
                    )

                }

            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 56.dp, bottom = 56.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        CustomTextView(
                            text = stringResource(id = R.string.my_balance),
                            fontWeight = 200,
                            fontSize = 14,
                            color = textColor
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Row {
                                CustomTextView(
                                    text = if (showBalance) moneyFormat(totalAmount.toString()) else "• •••",
                                    fontSize = 26
                                )

                                CustomTextView(
                                    modifier = Modifier
                                        .padding(start = 16.dp),
                                    text = stringResource(id = R.string.money),
                                    fontSize = 26,
                                    fontWeight = 600,
                                    color = Color(0xFF9B9B9B)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(96.dp))
                                    .clickable {
                                        showBalance = !showBalance
                                        onEventDispatcher(
                                            HomeContract.Intent.ChangeShowBalanceState(
                                                showBalance
                                            )
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(id = if (showBalance) R.drawable.ic_action_eye_open else R.drawable.ic_action_eye_close),
                                    contentDescription = null,
                                    tint = textColor
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                            .fillMaxWidth()
                            .height(220.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.pynet_card),
                                    fontSize = 18
                                )

                                CustomTextView(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = null
                                        ) { onEventDispatcher(HomeContract.Intent.OpenWhatIsScreen) }
                                        .align(Alignment.CenterEnd),
                                    text = stringResource(id = R.string.what_it),
                                    color = selectedItemColor,
                                    fontSize = 16
                                )
                            }

                            Row {
                                CustomImageView(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .padding(start = 12.dp, top = 8.dp, bottom = 16.dp),
                                    src = R.drawable.ic_payment_card
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                ) {
                                    CustomTextView(
                                        text = stringResource(id = R.string.pynet_card) + " • 1655",
                                        fontSize = 16,
                                        color = Color(0xFF505050)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(top = 4.dp),
                                            text = if (showBalance) "0" else "• •••",
                                            fontSize = 20
                                        )
                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(start = 8.dp),
                                            text = stringResource(id = R.string.money),
                                            fontSize = 20,
                                            color = Color(0xFF9B9B9B)
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
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
                                        ) { },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
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
                                        ) { onEventDispatcher(HomeContract.Intent.NavigateToTransferPage) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
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
                                        ) { onEventDispatcher(HomeContract.Intent.NavigateToPaymentPage) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
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
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CustomTextView(
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 16.dp),
                                text = stringResource(id = R.string.moneys),
                                fontSize = 16
                            )

                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 16.dp),
                                text = getCurrentDate(System.currentTimeMillis()),
                                fontSize = 14
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CustomImageView(
                                            modifier = Modifier
                                                .size(44.dp),
                                            src = R.drawable.ic_usa
                                        )

                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(top = 8.dp),
                                            text = "12889" + " " + stringResource(id = R.string.money)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CustomImageView(
                                            modifier = Modifier
                                                .size(44.dp),
                                            src = R.drawable.iz_ru
                                        )

                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(top = 8.dp),
                                            text = "137" + " " + stringResource(id = R.string.money)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CustomImageView(
                                            modifier = Modifier
                                                .size(44.dp),
                                            src = R.drawable.ic_china
                                        )

                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(top = 8.dp),
                                            text = "1757" + " " + stringResource(id = R.string.money)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(166.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Row {
                            CustomImageView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp)
                                    .size(72.dp),
                                src = R.drawable.ic_paynet_coins
                            )

                            Column(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp)
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.your_stage),
                                    fontSize = 13,
                                    color = Color(0xFF797777)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomTextView(
                                        text = "0",
                                        fontSize = 22
                                    )
                                    CustomTextView(
                                        modifier = Modifier
                                            .padding(start = 6.dp),
                                        text = stringResource(id = R.string.coin),
                                        fontSize = 18,
                                        color = Color(0xFF797777)
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(end = 16.dp, top = 4.dp)
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(
                                            color = Color(0xFFBDBBBB),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                ){}

                                CustomTextView(
                                    modifier = Modifier
                                        .padding(top = 6.dp),
                                    text = stringResource(id = R.string.next_stage),
                                    fontSize = 13,
                                    color = Color(0xFF797777)
                                )

                            }
                        }
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            onClick = { },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(Color.White),
                            border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Black))
                        ) {
                            CustomTextView(
                                text = stringResource(id = R.string.exchange),

                                )
                        }
                    }
                }

                item {
                    val radialGradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF00ED70), Color(0xFF00FFAC))
                    )
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(146.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(brush = radialGradient)
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 24.dp)
                                    .size(140.dp),
                                src = R.drawable.ic_securety_identification
                            )

                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.security_paynet),
                                    fontSize = 20
                                )
                                CustomTextView(
                                    modifier = Modifier
                                        .width(170.dp),
                                    text = stringResource(id = R.string.security),
                                    fontSize = 16,
                                    textAlign = TextAlign.Left,
                                    lineHeight = 18F
                                )

                                Button(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .height(38.dp),
                                    onClick = {  },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                                ) {
                                    CustomTextView(
                                        text = stringResource(id = R.string.be_secured),
                                        fontSize = 14
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(0.5F)
                                .fillMaxHeight(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                        ) {
                            when(list.size) {
                                0 -> {
                                    CardListIsEmpty { onEventDispatcher(HomeContract.Intent.OpenAddCardDialog) }
                                }
                                1 -> {
                                    CardListOneItem(
                                        list = list,
                                        clickCard = { onEventDispatcher(HomeContract.Intent.OpenCardDetailsScreen(it)) },
                                        clickAddCard = { onEventDispatcher(HomeContract.Intent.OpenAddCardDialog) }
                                    )
                                }
                                2 -> {
                                    CardListTwoItems(
                                        list = list,
                                        clickSecondCard = { onEventDispatcher(HomeContract.Intent.OpenCardDetailsScreen(it)) },
                                        clickFirstCard = { onEventDispatcher(HomeContract.Intent.OpenCardDetailsScreen(it)) },
                                        clickAddCard = { onEventDispatcher(HomeContract.Intent.OpenAddCardDialog) }
                                    )
                                }
                                else -> {
                                    CardListMoreItems(
                                        list = list,
                                        clickSecondCard = { onEventDispatcher(HomeContract.Intent.OpenCardDetailsScreen(it)) },
                                        clickFirstCard = { onEventDispatcher(HomeContract.Intent.OpenCardDetailsScreen(it)) },
                                        clickMyCards = { onEventDispatcher(HomeContract.Intent.OpenMyCardsScreen) }
                                    )
                                }
                            }
                        }
                        Card(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(0.5F)
                                .fillMaxHeight(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                CustomTextView(
                                    modifier = Modifier
                                        .padding(start = 12.dp, top = 12.dp),
                                    text = stringResource(id = R.string.kash),
                                    fontSize = 14
                                )
                                CustomTextView(
                                    modifier = Modifier
                                        .padding(start = 12.dp, top = 48.dp),
                                    text = stringResource(id = R.string.balance),
                                    fontSize = 14
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(start = 12.dp, top = 70.dp),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    CustomTextView(
                                        text = if (showBalance) "0" else "• •••",
                                        fontSize = 18
                                    )
                                    CustomTextView(
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                        text = stringResource(id = R.string.money),
                                        color = Color(0xFF9B9B9B),
                                        fontSize = 16
                                    )
                                }

                                Card(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(horizontal = 12.dp)
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        CustomTextView(
                                            modifier = Modifier
                                                .padding(top = 8.dp, start = 12.dp),
                                            text = stringResource(id = R.string.today),
                                            fontSize = 12,
                                            color = unSelectedItemColor
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(top = 8.dp, end = 12.dp)
                                                .align(AbsoluteAlignment.TopRight)
                                                .background(
                                                    color = selectedItemColor,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                        ) {
                                            CustomTextView(
                                                modifier = Modifier
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                text = "0 " + stringResource(id = R.string.money),
                                                fontSize = 10,
                                                color = Color.White
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(horizontal = 12.dp)
                                                    .padding(bottom = 12.dp)
                                                    .align(Alignment.BottomCenter)
                                                    .fillMaxWidth()
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = unSelectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = unSelectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = unSelectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = unSelectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = unSelectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 3.dp)
                                                        .width(16.dp)
                                                        .height(5.dp)
                                                        .background(
                                                            color = selectedItemColor,
                                                            shape = RoundedCornerShape(4.dp)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(146.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFFD2F5FC))
                                .fillMaxSize()
                        ) {
                            CustomImageView(
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 8.dp)
                                    .width(160.dp)
                                    .height(36.dp),
                                src = R.drawable.ic_paynet_avia_logo
                            )
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(200.dp),
                                src = R.drawable.ic_paynet_avia_image
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 46.dp),
                                text = stringResource(id = R.string.about_avia),
                                fontSize = 16,
                                fontWeight = 200
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, bottom = 16.dp)
                                    .height(38.dp),
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.buy_avia),
                                    fontSize = 16
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFFCFF6E8))
                                .fillMaxSize()
                        ) {
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp),
                                text = stringResource(id = R.string.my_home),
                                fontSize = 20,
                                fontWeight = 200
                            )
                            CustomImageView(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp)
                                    .align(Alignment.CenterEnd)
                                    .size(130.dp),
                                src = if (getCurrentLanguage() == "ru") R.drawable.ic_my_home_example_ru else R.drawable.ic_my_home_example_uz
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 46.dp)
                                    .width(200.dp),
                                text = stringResource(id = R.string.pay_for_home),
                                fontSize = 13,
                                fontWeight = 1,
                                textAlign = TextAlign.Left,
                                lineHeight = 16F
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, bottom = 16.dp)
                                    .height(34.dp),
                                shape = RoundedCornerShape(8.dp),
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(containerColor = selectedItemColor, contentColor = Color.White)
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.add_home),
                                    fontSize = 14,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                            .height(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFFE8F4FF))
                                .fillMaxSize()
                        ) {
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp),
                                text = stringResource(id = R.string.know_about),
                                fontSize = 20,
                                fontWeight = 200
                            )
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(120.dp),
                                src = R.drawable.ic_my_debts
                            )
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 46.dp),
                                text = stringResource(id = R.string.know_about_mib),
                                fontSize = 16,
                                fontWeight = 200
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, bottom = 16.dp)
                                    .height(38.dp),
                                onClick = {  },
                                elevation = null,
                                interactionSource = noRippleInteractionSource,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                CustomTextView(
                                    text = stringResource(id = R.string.be_secured),
                                    fontSize = 16
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CardListIsEmpty(clickAddCard: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { }

            ) {
                CustomTextView(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp),
                    text = stringResource(id = R.string.my_card),
                    fontSize = 14
                )
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 10.dp, top = 48.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) { clickAddCard() }
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomImageView(
                            modifier = Modifier
                                .size(64.dp),
                            src = R.drawable.ic_add_card
                        )
                        CustomTextView(
                            modifier = Modifier
                                .padding(top = 12.dp),
                            text = stringResource(id = R.string.add_card),
                            fontSize = 14
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CardListOneItem(
        list: List<CardData>,
        clickCard: (CardData) -> Unit,
        clickAddCard: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { clickAddCard() }

            ) {
                CustomTextView(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp),
                    text = stringResource(id = R.string.my_card),
                    fontSize = 14
                )
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 10.dp, top = 48.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickAddCard() }
                            .background(
                                color = Color(0x28808080),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 24.dp),
                            text = stringResource(id = R.string.add_card)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickCard(list[0]) }
                            .background(
                                brush = getGradient(list[0].themeType),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        if (getCardType(list[0].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .width(60.dp),
                                src = getCardType(list[0].pan)
                            )
                        }
                        else {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .size(48.dp),
                                src = getCardType(list[0].pan)
                            )
                        }
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp),
                            text = moneyFormat(list[0].amount.toString()) + " " + stringResource(id = R.string.money),
                            color = Color.White
                        )
                        if (getCardType(list[0].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Humo",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                        else {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Uzcard",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CardListTwoItems(
        list: List<CardData>,
        clickSecondCard: (CardData) -> Unit,
        clickFirstCard: (CardData) -> Unit,
        clickAddCard: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { clickAddCard() }

            ) {
                CustomTextView(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp),
                    text = stringResource(id = R.string.my_card),
                    fontSize = 14
                )
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, end = 8.dp)
                        .align(Alignment.TopEnd)
                        .background(color = selectedItemColor, shape = RoundedCornerShape(100))
                        .size(24.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .rotate(45F)
                            .size(13.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 10.dp, top = 48.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickFirstCard(list[0]) }
                            .background(
                                brush = getGradient(list[0].themeType),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .width(60.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        else {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .size(48.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp),
                            text = moneyFormat(list[0].amount.toString()) + " " + stringResource(id = R.string.money),
                            color = Color.White
                        )
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Humo",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                        else {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Uzcard",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickSecondCard(list[1]) }
                            .background(
                                brush = getGradient(list[1].themeType),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .width(60.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        else {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .size(48.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp),
                            text = moneyFormat(list[1].amount.toString()) + " " + stringResource(id = R.string.money),
                            color = Color.White
                        )
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Humo",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                        else {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Uzcard",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CardListMoreItems(
        list: List<CardData>,
        clickSecondCard: (CardData) -> Unit,
        clickFirstCard: (CardData) -> Unit,
        clickMyCards: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { clickMyCards() }

            ) {
                CustomTextView(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp),
                    text = stringResource(id = R.string.my_card),
                    fontSize = 14
                )
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, end = 8.dp)
                        .align(Alignment.TopEnd)
                        .background(color = Color(0xFF353535), shape = RoundedCornerShape(100))
                        .size(24.dp)
                ) {
                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "${list.size}",
                        fontSize = 14,
                        color = Color.White
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 10.dp, top = 48.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickFirstCard(list[0]) }
                            .background(
                                brush = getGradient(list[0].themeType),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        if (getCardType(list[0].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .width(60.dp),
                                src = getCardType(list[0].pan)
                            )
                        }
                        else {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .size(48.dp),
                                src = getCardType(list[0].pan)
                            )
                        }
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp),
                            text = moneyFormat(list[0].amount.toString()) + " " + stringResource(id = R.string.money),
                            color = Color.White
                        )
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Humo",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                        else {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Uzcard",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(94.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { clickSecondCard(list[1]) }
                            .background(
                                brush = getGradient(list[1].themeType),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .width(60.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        else {
                            CustomImageView(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp)
                                    .size(48.dp),
                                src = getCardType(list[1].pan)
                            )
                        }
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp),
                            text = moneyFormat(list[1].amount.toString()) + " " + stringResource(id = R.string.money),
                            color = Color.White
                        )

                        if (getCardType(list[1].pan) == R.drawable.ic_paymentsystem_humo) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Humo",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                        else {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 8.dp, start = 12.dp),
                                text = "Uzcard",
                                color = Color.White,
                                fontSize = 14
                            )
                        }
                    }
                }
            }
        }
    }

}
