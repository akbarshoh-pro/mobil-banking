package com.example.mobilebank.presentation.screens.add_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomButton
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextField
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.colorInputBg
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.ui.theme.unSelectedItemColor
import com.example.mobilebank.utils.CardNumberVisualTransformation
import com.example.mobilebank.utils.ExpiryDateTransformation
import com.example.mobilebank.utils.dateFormatIsCorrect
import org.orbitmvi.orbit.compose.collectAsState

class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AddCardContract.ViewModel = getViewModel<AddCardViewModel>()
        AddCardContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun AddCardContent(
    uiState: State<AddCardContract.UIState>,
    onEventDispatcher: (AddCardContract.Intent) -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0063B5), Color(0xFF00EBC8))
    )
    var cardNumber by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var correctDateFormat by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    CustomAppBar(
        title = stringResource(id = R.string.add_card_screen),
        onClick = { onEventDispatcher(AddCardContract.Intent.BackToMainScreen) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .height(220.dp)
                    .background(
                        brush = gradient,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = colorInputBg, shape = RoundedCornerShape(16.dp))
                ) {

                    CustomTextField(
                        value = cardNumber,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() } && it.length <= 16) {
                                cardNumber = it
                                if (it.length == 16)
                                    focusManager.moveFocus(FocusDirection.Next)

                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .width(280.dp),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        visualTransformation = CardNumberVisualTransformation(),
                        keyboardActions = KeyboardActions.Default,
                        cursorBrush = SolidColor(selectedItemColor),
                        fontSize = 18,
                        fontWeight = 600,
                    )

                    if (cardNumber.isEmpty()) {
                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.CenterStart),
                            text = stringResource(id = R.string.card_number),
                            color = unSelectedItemColor,
                            fontSize = 18,
                            fontWeight = 600,
                        )
                    }

                    CustomImageView(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .size(25.dp),
                        src = R.drawable.ic_action_scan_card,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 96.dp)
                        .width(100.dp)
                        .height(56.dp)
                        .background(color = colorInputBg, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = date,
                        onValueChange = {
                            if (it.length <= 4) {
                                date = it
                                if (it.length == 4)
                                    correctDateFormat = dateFormatIsCorrect(date)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = ExpiryDateTransformation(),
                        keyboardActions = KeyboardActions.Default,
                        cursorBrush = SolidColor(selectedItemColor),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                            fontWeight = FontWeight(600),
                        )
                    )

                    if (date.isEmpty()) {
                        CustomTextView(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.CenterStart),
                            text = stringResource(id = R.string.mm_yy),
                            color = unSelectedItemColor,
                            fontSize = 18,
                            fontWeight = 600,
                        )
                    }
                }

                if (correctDateFormat) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 160.dp),
                        text = stringResource(id = R.string.date_card_fail),
                        color = Color(0xFFFF1616),
                        fontSize = 14,
                        textAlign = TextAlign.Left
                    )
                }

                if (!uiState.value.requestIsSuccess) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 160.dp),
                        text = stringResource(id = R.string.card_number_fail),
                        color = Color(0xFFFF1616),
                        fontSize = 14,
                        textAlign = TextAlign.Left
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
                    text = stringResource(id = R.string.continue_btn),
                    fontSize = 20,
                    fontWeight = 700,
                    textColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    enabled = cardNumber.length == 16 && !correctDateFormat,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = btnNextDisable,
                        disabledContentColor = textNextDisable,
                        contentColor = textNextEnable,
                        containerColor = btnNextEnable
                    ),
                    click = {
                        onEventDispatcher(AddCardContract.Intent.AddCard(
                            cardNumber = cardNumber,
                            year = "20${date.substring(2)}",
                            month = date.substring(0,2)
                        ))
                    }
                )
            }
        }
    }
}

