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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.utils.openUrl

class AboutDialog : Screen {
    @Composable
    override fun Content() {
        AboutDialogContent(LocalContext.current)
    }

    @Composable
    private fun AboutDialogContent(context: Context) {
        val annotatedLink = buildAnnotatedString {
            val str = "www.paynet.uz"
            val startIndex = 0
            val endIndex = str.length
            append(str)
            addStyle(
                style = SpanStyle(
                    color = Color(0xff64B5F6),
                    textDecoration = TextDecoration.Underline
                ), start = startIndex, end = endIndex
            )
        }
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
            CustomImageView(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp),
                src = R.drawable.ic_paynet_
            )
            CustomTextView(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                text = stringResource(id = R.string.about_paynet),
                fontSize = 14,
                textAlign = TextAlign.Left,
                color = Color(0xFF585858)
            )
            CustomTextView(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                text = stringResource(id = R.string.paynet_kompany),
                fontSize = 14,
                textAlign = TextAlign.Left,
                color = Color(0xFF585858)
            )
            CustomTextView(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                text = "Versiya: 1.0.0",
                fontSize = 14,
                textAlign = TextAlign.Left,
                color = Color(0xFF585858)
            )
            Text(
                text = annotatedLink,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        openUrl(context, "https://www.paynet.uz")
                    }
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                color = Color(0xFF585858)
            )
        }
    }

}