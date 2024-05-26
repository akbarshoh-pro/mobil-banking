package com.example.mobilebank.presentation.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.openUrl
import com.example.mobilebank.utils.setLocale
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

class SettingsScreen : Screen {


    @Composable
    override fun Content() {
        val viewModel: SettingsContract.ViewModel = getViewModel<SettingsViewModel>()
        viewModel.onEventDispatcher(SettingsContract.Intent.LoadAppSettings)
        SettingsContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }

    @Composable
    fun SettingsContent(
        uiState: State<SettingsContract.UIState>,
        onEventDispatcher: (SettingsContract.Intent) -> Unit,

    ) {
        var language by remember { mutableStateOf(uiState.value.lang) }
        var lang by remember { mutableStateOf(getCurrentLanguage()) }
        var switch by remember { mutableStateOf(uiState.value.biometry) }
        val context = LocalContext.current

        if (lang != "") {
            CustomAppBar(
                title = stringResource(id = R.string.app_set),
                onClick = { onEventDispatcher(SettingsContract.Intent.BackProfileScreen) }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
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
                                text = stringResource(id = R.string.app_lang)
                            )

                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 12.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                                    .height(110.dp)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .weight(1F)
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = null
                                        ) {
                                            language = true
                                            lang = "uz"
                                            setLocale(context, "uz")
                                        }
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(3.dp),
                                    border = if (language) BorderStroke(1.5.dp, selectedItemColor) else BorderStroke(0.dp, Color.Transparent)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        CustomImageView(
                                            modifier = Modifier
                                                .padding(start = 12.dp, top = 12.dp),
                                            src = R.drawable.uz
                                        )
                                        CustomTextView(
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .padding(start = 12.dp, bottom = 12.dp),
                                            text = "O'zbek"
                                        )
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1F)
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = null
                                        ) {
                                            language = false
                                            lang = "ru"
                                            setLocale(context, "ru")
                                        }
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(3.dp),
                                    border = if (!language) BorderStroke(1.5.dp, selectedItemColor) else BorderStroke(0.dp, Color.Transparent)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        CustomImageView(
                                            modifier = Modifier
                                                .padding(start = 12.dp, top = 12.dp),
                                            src = R.drawable.ru
                                        )
                                        CustomTextView(
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .padding(start = 12.dp, bottom = 12.dp),
                                            text = "Русский"
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
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            CustomTextView(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 10.dp),
                                text = stringResource(id = R.string.app_security),
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
                                    src = R.drawable.ic_status_lock
                                )
                                CustomTextView(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(start = 46.dp),
                                    text = stringResource(id = R.string.change_pin)
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
                                    ) { switch = !switch }
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.ic_touch),
                                    contentDescription = null
                                )

                                CustomTextView(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(start = 46.dp),
                                    text = stringResource(id = R.string.touch)
                                )
                                Switch(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd),
                                    checked = switch,
                                    onCheckedChange = {
                                        switch = it
                                        onEventDispatcher(SettingsContract.Intent.SetBiometryUnlock(switch))
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color(0xFF00A826),
                                        checkedTrackColor = Color(0xFF74D497),
                                        uncheckedBorderColor = Color.Transparent,
                                        uncheckedThumbColor = Color.White,
                                        uncheckedTrackColor = Color(0xFFCCCBCB)
                                    )
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
                                    src = R.drawable.ic_action_phones
                                )
                                CustomTextView(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(start = 46.dp),
                                    text = stringResource(id = R.string.users)
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
                }
            }
        }
    }

}