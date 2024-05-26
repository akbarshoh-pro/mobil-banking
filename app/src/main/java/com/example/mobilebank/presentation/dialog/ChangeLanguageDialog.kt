package com.example.mobilebank.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
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
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.setLocale

class ChangeLanguageDialog(
    val blockRu: () -> Unit = {},
    val blockUz: () -> Unit = {},
    val clickCancel: () -> Unit = {}
) : Screen {
    @Composable
    override fun Content() {
        ChangeLanguageContent(
            clickUz = { blockUz() },
            clickRu = { blockRu() },
            clickCancel = { clickCancel() }
        )
    }

    @Composable
    fun ChangeLanguageContent(
        clickUz: () -> Unit,
        clickRu: () -> Unit,
        clickCancel: () -> Unit
    ) {
        var btnUz by remember { mutableStateOf(getCurrentLanguage() == "uz") }
        var btnRu by remember { mutableStateOf(getCurrentLanguage() == "ru") }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                        .width(30.dp)
                        .height(4.dp)
                        .background(Color(0xFFADADAD), shape = RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 12.dp)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) { clickCancel() }
                        .background(color = Color(0xFFD6D5D5), shape = RoundedCornerShape(100))
                        .size(32.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(16.dp),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color(0xFF616060)
                    )
                }
            }

            CustomTextView(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.app_lang),
                fontSize = 22
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (btnRu) Color.White else Color.Transparent),
                elevation = if (btnRu) ButtonDefaults.elevation(2.dp) else ButtonDefaults.elevation(
                    0.dp
                ),
                onClick = {
                    btnRu = !btnRu
                    btnUz = !btnUz
                    clickRu()
                }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxSize()
                ) {
                    CustomImageView(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .height(26.dp),
                        src = R.drawable.ru
                    )
                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 56.dp),
                        text = "Русский",
                        fontSize = 18
                    )
                    if (btnRu) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(36.dp),
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = selectedItemColor
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (btnUz) Color.White else Color.Transparent),
                elevation = if (btnUz) ButtonDefaults.elevation(2.dp) else ButtonDefaults.elevation(
                    0.dp
                ),
                onClick = {
                    btnRu = !btnRu
                    btnUz = !btnUz
                    clickUz()
                }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxSize()
                ) {
                    CustomImageView(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .height(26.dp),
                        src = R.drawable.uz
                    )
                    CustomTextView(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 56.dp),
                        text = "O'zbek",
                        fontSize = 18
                    )
                    if (btnUz) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(36.dp),
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = selectedItemColor
                        )
                    }
                }
            }
        }
    }
}