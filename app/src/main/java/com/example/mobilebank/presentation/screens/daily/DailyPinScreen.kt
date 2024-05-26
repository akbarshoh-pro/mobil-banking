package com.example.mobilebank.presentation.screens.daily

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.presentation.dialog.UpdatePinDialog
import com.example.mobilebank.ui.components.CirclePin
import com.example.mobilebank.ui.components.CustomKeyboard
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.checkColor
import com.example.mobilebank.utils.maskPhoneNumber
import com.example.mobilebank.utils.requireBiometricAuth
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


class DailyPinScreen : Screen {
    @Composable
    override fun Content() {
        MyDataLoader.loadCardsData()
        MyDataLoader.loadUserData()
        val viewModel: DailyPinContract.ViewModel = getViewModel<DailyPinViewModel>()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val context = LocalContext.current
        viewModel.onEventDispatcher(DailyPinContract.Intent.GetData)

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                DailyPinContract.SideEffect.OpenUpdatePinDialog -> {
                    bottomSheetNavigator.show(
                        UpdatePinDialog {
                            bottomSheetNavigator.hide()
                            viewModel.onEventDispatcher(DailyPinContract.Intent.UserUnRegister)
                        }
                    )
                }
            }
        }

        context.requireBiometricAuth(
            onSuccess = {
                viewModel.onEventDispatcher(DailyPinContract.Intent.NavigateToMainScreen)
            },
            onFailed = {},
            onError = { _, _ -> }
        )

        DailyPinContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun DailyPinContent(
    uiState: State<DailyPinContract.UIState>,
    onEventDispatcher: (DailyPinContract.Intent) -> Unit
) {
    var colorOne by remember { mutableStateOf(Color(0xFFF3F4FA)) }
    var colorTwo by remember { mutableStateOf(Color(0xFFF3F4FA)) }
    var colorThree by remember { mutableStateOf(Color(0xFFF3F4FA)) }
    var colorFour by remember { mutableStateOf(Color(0xFFF3F4FA)) }
    val lsColor by remember { mutableStateOf(arrayListOf(0, 0, 0, 0)) }
    val lsNum by remember { mutableStateOf(arrayListOf(-1, -1, -1, -1)) }
    var animCount by remember { mutableStateOf(0) }
    var checkedIndex by remember { mutableStateOf(-1) }

    val offsetDistance = 8.dp

    var trigger by remember { mutableStateOf(false) }

    val animatedOffset by animateFloatAsState(
        targetValue = if (trigger) offsetDistance.value else -offsetDistance.value,
        animationSpec = tween(durationMillis = 60),
        finishedListener = {
            if (animCount == 1) {
                trigger = !trigger
                animCount ++
            } else {
                animCount = 0
                lsColor[0] = 0
                lsColor[1] = 0
                lsColor[2] = 0
                lsColor[3] = 0
                checkedIndex = -1
                colorOne = checkColor(lsColor[0])
                colorTwo = checkColor(lsColor[1])
                colorThree = checkColor(lsColor[2])
                colorFour = checkColor(lsColor[3])
            }
        },
        label = ""
    )


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_log),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
            )

            CustomTextView(
                text = stringResource(id = R.string.enter_pin),
                modifier = Modifier
                    .padding(top = 12.dp),
                fontSize = 24,
                fontWeight = 800
            )

            CustomTextView(
                text = stringResource(id = R.string.phone),
                modifier = Modifier
                    .padding(top = 4.dp),
                fontSize = 14,
                fontWeight = 400
            )

            CustomTextView(
                text = maskPhoneNumber(uiState.value.phone),
                modifier = Modifier
                    .padding(top = 4.dp),
                fontSize = 14,
                fontWeight = 800
            )

        }

        Row (
            modifier = Modifier
                .offset(x = if (checkedIndex == 3 && lsNum.joinToString() != uiState.value.code) animatedOffset.dp else 0.dp)
                .align(Alignment.Center)
                .padding(bottom = 160.dp)
        ) {
            CirclePin(
                color = colorOne
            )
            CirclePin(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = colorTwo
            )
            CirclePin(
                modifier = Modifier
                    .padding(end = 16.dp),
                color = colorThree
            )
            CirclePin(
                color = colorFour
            )
        }

        CustomKeyboard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp),
            block = {
                if (checkedIndex < 3) {
                    checkedIndex++
                    lsColor[checkedIndex] = 1
                    lsNum[checkedIndex] = it
                    when(checkedIndex) {
                        0 -> { colorOne = checkColor(lsColor[checkedIndex]) }
                        1 -> { colorTwo = checkColor(lsColor[checkedIndex]) }
                        2 -> { colorThree = checkColor(lsColor[checkedIndex]) }
                        3 -> { colorFour = checkColor(lsColor[checkedIndex]) }
                    }
                }

                if (checkedIndex == 3 && lsNum.joinToString() == uiState.value.code) {
                    onEventDispatcher(DailyPinContract.Intent.NavigateToMainScreen)
                } else if (checkedIndex == 3 && lsNum.joinToString() != uiState.value.code) {
                    trigger = !trigger
                    animCount++
                    colorOne = Color(0xFFBB4242)
                    colorTwo = Color(0xFFBB4242)
                    colorThree = Color(0xFFBB4242)
                    colorFour = Color(0xFFBB4242)
                }
            },
            clear = {
                if (checkedIndex > -1) {
                    lsColor[checkedIndex] = 0
                    lsNum[checkedIndex] = -1
                    when(checkedIndex) {
                        0 -> { colorOne = checkColor(lsColor[checkedIndex]) }
                        1 -> { colorTwo = checkColor(lsColor[checkedIndex]) }
                        2 -> { colorThree = checkColor(lsColor[checkedIndex]) }
                        3 -> { colorFour = checkColor(lsColor[checkedIndex]) }
                    }
                    checkedIndex--
                }
            },
            itemModifier = Modifier
                .clickable { onEventDispatcher(DailyPinContract.Intent.OpenUpdatePinDialog) }
        ){}

        Text(
            text = stringResource(id = R.string.forget_pin),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
            color = Color(0xFF7A7878),
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(bottom = 56.dp, end = 200.dp)
                .size(64.dp)
                .align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,

        )
    }
}


