package com.example.mobilebank.presentation.screens.update_data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomButton
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.utils.DateVisualTransformation
import org.orbitmvi.orbit.compose.collectAsState

class UpdateDataScreen: Screen {
    @Composable
    override fun Content() {
        var viewModel: UpdateDataContract.ViewModel = getViewModel<UpdateDataViewModel>()
        TransferPercentContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun TransferPercentContent(
    uiState: State<UpdateDataContract.UIState>,
    onEventDispatcher: (UpdateDataContract.Intent) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    CustomAppBar(
        title = stringResource(id = R.string.add_my_datas),
        onClick = { onEventDispatcher(UpdateDataContract.Intent.BackProfileScreen) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults
                    .cardColors(Color.White),
                elevation = CardDefaults
                    .elevatedCardElevation(3.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 12.dp),
                        text = stringResource(id = R.string.enter_data),
                        fontSize = 18,
                        fontWeight = 600
                    )
                    Box(
                        Modifier
                            .padding(horizontal = 12.dp)
                            .height(56.dp)
                            .background(Color(0xFFE7E5E5), RoundedCornerShape(12.dp))
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            value = lastName,
                            onValueChange = { value ->
                                if (value.length < 15) {
                                    lastName = value
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp
                            )
                        )
                        if (lastName.isEmpty()) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 16.dp),
                                text = stringResource(id = R.string.enter_last),
                                fontSize = 20
                            )
                        }
                    }

                    Box(
                        Modifier
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .height(56.dp)
                            .background(Color(0xFFE7E5E5), RoundedCornerShape(12.dp))
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            value = firstName,
                            onValueChange = { value ->
                                if (value.length < 15) {
                                    firstName = value
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = TextStyle(
                                fontSize = 20.sp
                            )
                        )
                        if (firstName.isEmpty()) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 16.dp),
                                text = stringResource(id = R.string.enter_name),
                                fontSize = 20
                            )
                        }
                    }

                    Box(
                        Modifier
                            .padding(bottom = 8.dp)
                            .padding(horizontal = 12.dp)
                            .height(56.dp)
                            .background(Color(0xFFE7E5E5), RoundedCornerShape(12.dp))
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            value = date,
                            onValueChange = { value ->
                                if (value.length < 9 && value.isDigitsOnly()) {
                                    date = value
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = DateVisualTransformation(),
                            textStyle = TextStyle(
                                fontSize = 20.sp
                            )
                        )
                        if (date.isEmpty()) {
                            CustomTextView(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 16.dp),
                                text = stringResource(id = R.string.enter_born_date),
                                fontSize = 20
                            )
                        }
                    }
                    if (date.length == 8 && (date.substring(0, 2).toInt() !in 1 .. 31 || date.substring(2, 4).toInt() !in 1 .. 12 || date.substring(4).toInt() !in 1900 .. 2100)){
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 16.dp, bottom = 12.dp),
                            text = stringResource(id = R.string.error_date),
                            color = Color(0xFFB16262),
                            textAlign = TextAlign.Left
                        )
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
                    enabled = firstName.length in 4 .. 15 && lastName.length in 4 .. 15 && date.length == 8 && date.substring(0, 2).toInt() in 1 .. 31 && date.substring(2, 4).toInt() in 1 .. 12 && date.substring(4).toInt() in 1900 .. 2100,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = btnNextDisable,
                        disabledContentColor = textNextDisable,
                        contentColor = textNextEnable,
                        containerColor = btnNextEnable
                    ),
                    click = { onEventDispatcher(UpdateDataContract.Intent.UpdateData(firstName, lastName, date)) }
                )
            }
        }
    }
}