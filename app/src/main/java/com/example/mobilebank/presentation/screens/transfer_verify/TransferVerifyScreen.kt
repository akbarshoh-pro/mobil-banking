package com.example.mobilebank.presentation.screens.transfer_verify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.ui.components.CodeTextField
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnRetryDisable
import com.example.mobilebank.ui.theme.btnRetryEnable
import com.example.mobilebank.ui.theme.textRetryDisable
import com.example.mobilebank.ui.theme.textRetryEnable
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.maskPhoneNumber
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState

class TransferVerifyScreen(
    private val data: UserCardData,
    private val amount: String,
    private val token: String
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: TransferVerifyContract.ViewModel = getViewModel<TransferVerifyViewModel>()
        viewModel.onEventDispatcher(TransferVerifyContract.Intent.GetData)
        TransferVerifyContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher,
            amount = amount,
            data = data,
            token = token
        )
    }
}

@Composable
fun TransferVerifyContent(
    uiState: State<TransferVerifyContract.UIState>,
    onEventDispatcher: (TransferVerifyContract.Intent) -> Unit,
    amount: String,
    data: UserCardData,
    token: String
) {
    val noRippleInteractionSource = remember { MutableInteractionSource() }
    var phoneSt by remember { mutableStateOf("") }
    var buttonSt by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var timeRemaining by remember { mutableStateOf(59) }
    var requestIsSend by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var btnState by remember { mutableStateOf(false) }
    var tokenRequest = ""
    var phone = ""

    when(uiState.value) {
        is TransferVerifyContract.UIState.UserPhoneNumber -> {
            phone = (uiState.value as TransferVerifyContract.UIState.UserPhoneNumber).phone
        }
        is TransferVerifyContract.UIState.RequestToken -> {
            tokenRequest = (uiState.value as TransferVerifyContract.UIState.RequestToken).token
        }
        TransferVerifyContract.UIState.ErrorMes -> {
            error = true
        }
    }

    LaunchedEffect(true) {
        while (timeRemaining > 0) {
            delay(1000)
            timeRemaining--
            btnState = timeRemaining == 0
        }
    }


    if (phoneSt.length == 6) {
        onEventDispatcher(TransferVerifyContract.Intent.NavigateTransferSuccessScreen(if (requestIsSend) tokenRequest else token, phoneSt, data, amount))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = noRippleInteractionSource
                ) { onEventDispatcher(TransferVerifyContract.Intent.BackToTransferCardScreen) }
                .padding(24.dp)
                .size(24.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
            )

            Text(
                text = stringResource(id = R.string.enter_code),
                fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                fontWeight = FontWeight(800),
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 12.dp)
            )

            Row {
                Text(
                    text = if (getCurrentLanguage() == "ru") stringResource(id = R.string.sent_it_to)  else "${maskPhoneNumber(phone)} ",
                    fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                    fontWeight = FontWeight(100),
                    fontSize = 14.sp,
                    color = Color(0xFF6D6D6D),
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
                Text(
                    text = if (getCurrentLanguage() == "ru") " ${maskPhoneNumber(phone)}" else stringResource(id = R.string.sent_it_to),
                    fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                    fontWeight = FontWeight(100),
                    fontSize = 14.sp,
                    color = Color(0xFF6D6D6D),
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }

            CodeTextField(
                value = phoneSt,
                length = 6,
                modifier = Modifier
                    .padding(top = 16.dp, start = 4.dp, end = 4.dp)
            ) {
                if (it.length < 6)
                    error = false

                phoneSt = it
                if (it.length < 6) {
                    buttonSt = false
                } else {
                    buttonSt = true
                    focusManager.clearFocus()
                }
            }

            if (error) {
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp),
                    text = stringResource(id = R.string.code_icnt_correct),
                    color = Color(0xFFE05E5E)
                )
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = btnRetryDisable,
                disabledContentColor = textRetryDisable,
                contentColor = textRetryEnable,
                containerColor = btnRetryEnable
            ),
            enabled = btnState,
            onClick = {
                onEventDispatcher(TransferVerifyContract.Intent.ResendCode(token))
                requestIsSend = true
                btnState = false
            }
        ) {

            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_retry),
                contentDescription = null
            )

            CustomTextView(
                modifier = Modifier
                    .padding(end = 36.dp),
                text = stringResource(id = R.string.send_new_code),
                fontSize = 20,
                fontWeight = 700,
                color = Color.White
            )

            CustomTextView(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = if (timeRemaining > 0) timeRemaining.toString() else "",
                fontSize = 20,
                color = Color(0xFF777676),
                fontWeight = 700
            )

        }

    }
}


