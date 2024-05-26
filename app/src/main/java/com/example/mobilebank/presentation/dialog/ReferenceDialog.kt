package com.example.mobilebank.presentation.dialog

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.utils.openUrl


class ReferenceDialog : Screen {
    @Composable
    override fun Content() {
        ReferenceDialogContent(LocalContext.current)
    }

    @Composable
    private fun ReferenceDialogContent(context: Context) {
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
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .width(30.dp)
                    .height(4.dp)
                    .background(Color(0xFFADADAD), shape = RoundedCornerShape(4.dp))
            )
            CustomTextView(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp),
                text = stringResource(id = R.string.reference)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { openUrl(context, "https://assets-global.website-files.com/63a7038e6eb0c1f38cd4d11f/659e7e324fed06afd1dbacfb_оферта%20мобилка%202024.pdf") }
                    .fillMaxWidth()
            ) {
                CustomImageView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(22.dp),
                    src = R.drawable.ic_detail
                )
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 36.dp)
                        .padding(start = 46.dp),
                    text = stringResource(id = R.string.offer),
                    fontSize = 14,
                    textAlign = TextAlign.Left
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
                    ) { openUrl(context, "https://assets-global.website-files.com/63a7038e6eb0c1f38cd4d11f/6602de34fdf497829debb1df_оферта%20финал%20final.pdf") }
                    .fillMaxWidth()
            ) {
                CustomImageView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(22.dp),
                    src = R.drawable.ic_detail
                )
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 36.dp)
                        .padding(start = 46.dp),
                    text = stringResource(id = R.string.offer_bonus_card),
                    fontSize = 14,
                    textAlign = TextAlign.Left
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
                    .padding(top = 12.dp, bottom = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { openUrl(context, "https://uploads-ssl.webflow.com/63a7038e6eb0c1f38cd4d11f/64b8ecef9bd4e117c602cbc9_оферта%20кошелька%20(2).pdf") }
                    .fillMaxWidth()
            ) {
                CustomImageView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(22.dp),
                    src = R.drawable.ic_detail
                )
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 36.dp)
                        .padding(start = 46.dp),
                    text = stringResource(id = R.string.offer_system),
                    fontSize = 14,
                    textAlign = TextAlign.Left,
                    lineHeight = 16F
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

