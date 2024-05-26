package com.example.mobilebank.presentation.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.presentation.dialog.AboutDialog
import com.example.mobilebank.presentation.dialog.LogOutDialog
import com.example.mobilebank.presentation.dialog.RateDialog
import com.example.mobilebank.presentation.dialog.ReferenceDialog
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.bgWithCards
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.utils.formatDate
import com.example.mobilebank.utils.formatPhoneNumber
import com.example.mobilebank.utils.openUrl
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProfileContract.ViewModel = getViewModel<ProfileViewModel>()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        viewModel.onEventDispatcher(ProfileContract.Intent.GetUserData)
        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                ProfileContract.SideEffect.OpenAboutDialog -> {
                    bottomSheetNavigator.show(
                        AboutDialog()
                    )
                }
                ProfileContract.SideEffect.OpenOffersDialog -> {
                    bottomSheetNavigator.show(
                        ReferenceDialog()
                    )
                }
                ProfileContract.SideEffect.OpenRateDialog -> {
                    bottomSheetNavigator.show(
                        RateDialog()
                    )
                }
                ProfileContract.SideEffect.OpenLogOutDialog -> {
                    bottomSheetNavigator.show(
                        LogOutDialog(
                            logOut = {
                                viewModel.onEventDispatcher(ProfileContract.Intent.LogOut)
                                bottomSheetNavigator.hide()
                            },
                            dismiss = {
                                bottomSheetNavigator.hide()
                            }
                        )
                    )
                }
            }
        }
        ProfileContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher,
            context = LocalContext.current
        )
    }
}

@Composable
private fun ProfileContent(
    uiState: State<ProfileContract.UIState>,
    onEventDispatcher: (ProfileContract.Intent) -> Unit,
    context: Context
) {
    CustomAppBar(
        title = stringResource(id = R.string.profile),
        onClick = { onEventDispatcher(ProfileContract.Intent.BackScreen) }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp),
                        text = formatPhoneNumber(uiState.value.data.phone)
                    )

                    if (uiState.value.data.firstName == "Default" && uiState.value.data.lastName == "Default") {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(3.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(6.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.sad_emoji),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .size(34.dp)
                                )

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = AbsoluteAlignment.Left,
                                    modifier = Modifier
                                        .padding(top = 4.dp, start = 12.dp)
                                        .wrapContentSize()
                                ) {
                                    CustomTextView(
                                        stringResource(id = R.string.your_status),
                                        fontSize = 16,
                                    )

                                    CustomTextView(
                                        stringResource(id = R.string.anonim),
                                        fontSize = 18,
                                    )
                                }
                            }
                        }

                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp, top = 16.dp)
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = btnNextDisable,
                                disabledContentColor = textNextDisable,
                                contentColor = textNextEnable,
                                containerColor = btnNextEnable
                            ),
                            onClick = { onEventDispatcher(ProfileContract.Intent.NavigateUpdateDataScreen) }
                        ) {
                            CustomTextView(
                                text = stringResource(id = R.string.change_status),
                                fontSize = 20,
                                fontWeight = 700,
                                color = textNextEnable
                            )
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 12.dp, bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(3.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(6.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.app_logo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .size(34.dp)
                                )

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = AbsoluteAlignment.Left,
                                    modifier = Modifier
                                        .padding(top = 4.dp, start = 12.dp)
                                        .wrapContentSize()
                                ) {
                                    CustomTextView(
                                        text = uiState.value.data.lastName + " " + uiState.value.data.firstName,
                                        fontSize = 18,
                                    )

                                    CustomTextView(
                                        text = formatDate(uiState.value.data.bornDate),
                                        fontSize = 16,
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
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, )
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp),
                        text = stringResource(id = R.string.support),
                        fontSize = 18
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { openUrl(context, "https://chat.paynet.uz") }
                            .fillMaxWidth()
                    ) {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(32.dp),
                            src = R.drawable.ic_operations_comment
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.chat_assistant)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { }
                            .fillMaxWidth()
                    ) {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(28.dp),
                            src = R.drawable.ic_action_phone_alt
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.call_support)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 12.dp)
                            .fillMaxWidth()
                    ) {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(28.dp),
                            src = R.drawable.ic_action_mail
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.write_support)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    CustomTextView(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp),
                        text = stringResource(id = R.string.useful),
                        fontSize = 18
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onEventDispatcher(ProfileContract.Intent.OpenAboutDialog) }
                            .fillMaxWidth()
                    ) {
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(28.dp),
                            src = R.drawable.ic_assist
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.about)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onEventDispatcher(ProfileContract.Intent.OpenOffersDialog) }
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(24.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_question_mark_circle),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.reference)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onEventDispatcher(ProfileContract.Intent.NavigateMapScreen) }
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(28.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.map_office)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp, bottom = 12.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) { onEventDispatcher(ProfileContract.Intent.NavigateSettingsScreen) }
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(28.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_actions_settings),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.settings)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onEventDispatcher(ProfileContract.Intent.OpenRateDialog) }
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp, bottom = 12.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(28.dp),
                            tint = Color(0xFF6E7279),
                            painter = painterResource(id = R.drawable.ic_operations_star_v2),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.rate_app)
                        )
                        CustomImageView(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp),
                            src = R.drawable.ic_right
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onEventDispatcher(ProfileContract.Intent.OpenLogOutDialog) }
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp, bottom = 12.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 2.dp)
                                .size(28.dp),
                            tint = Color(0xFFDF392D),
                            painter = painterResource(id = R.drawable.ic_action_sign_in),
                            contentDescription = null
                        )
                        CustomTextView(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 46.dp),
                            text = stringResource(id = R.string.log_out),
                            color = Color(0xFFDF392D)
                        )
                    }
                }
            }
        }
    }
}
