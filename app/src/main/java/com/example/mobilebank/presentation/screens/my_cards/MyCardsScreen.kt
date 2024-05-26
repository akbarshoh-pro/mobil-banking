package com.example.mobilebank.presentation.screens.my_cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.presentation.dialog.AddCardDialog
import com.example.mobilebank.presentation.screens.my_cards.component.CardItem
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomButton
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.utils.getGradient
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MyCardsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MyCardsContract.ViewModel = getViewModel<MyCardsViewModel>()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        viewModel.onEventDispatcher(MyCardsContract.Intent.GetAllCards)

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                MyCardsContract.SideEffect.OpenAddCardDialog -> {
                    bottomSheetNavigator.show(
                        AddCardDialog(
                            blockUz = {
                                bottomSheetNavigator.hide()
                                viewModel.onEventDispatcher(MyCardsContract.Intent.OpenAddCardScreen)
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
            }
        }
        MyCardsContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }

    @Composable
    fun MyCardsContent(
        uiState: State<MyCardsContract.UIState>,
        onEventDispatcher: (MyCardsContract.Intent) -> Unit
    ) {
        val list = uiState.value.ls

        CustomAppBar(
            title = stringResource(id = R.string.my_cards),
            onClick = { onEventDispatcher(MyCardsContract.Intent.BackToHomeScreen) }
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(it)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 96.dp)
                        .fillMaxSize()
                ) {
                    items(list.size) {
                        CardItem(
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = if (list.size - 1 == it) 8.dp else 0.dp),
                            data = list[it],
                            gradient = getGradient(list[it].themeType),
                            block = { onEventDispatcher(MyCardsContract.Intent.OpenCardDetailsScreen(it)) }
                        )
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
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = btnNextDisable,
                            disabledContentColor = textNextDisable,
                            contentColor = textNextEnable,
                            containerColor = btnNextEnable
                        ),
                        text = stringResource(id = R.string.add_card),
                        fontSize = 20,
                        fontWeight = 700,
                        textColor = Color.White,
                        click = { onEventDispatcher(MyCardsContract.Intent.OpenAddCardDialog) },
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }
    }

}
