package com.example.mobilebank.presentation.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.selectedItemColor

class RateDialog : Screen {
    @Composable
    override fun Content() {
        RateDialogContent()
    }

    @Composable
    private fun RateDialogContent() {
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
                text = stringResource(id = R.string.how_paynet),
                fontSize = 17
            )
            CustomTextView(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp, end = 24.dp),
                text = stringResource(id = R.string.we_listen_you),
                textAlign = TextAlign.Left,
                fontSize = 14,
                color = Color(0xFF6D6C6C)
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(selectedItemColor),
                elevation = ButtonDefaults.buttonElevation(2.dp),
                onClick = {  }
            ) {
                CustomTextView(
                    text = stringResource(id = R.string.all_succes),
                    color = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 8.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                border = BorderStroke(width = 1.dp, brush = Brush.horizontalGradient(colors = listOf(Color.Black, Color.Black))),
                colors = ButtonDefaults.buttonColors(Color.White),
                elevation = ButtonDefaults.buttonElevation(1.dp),
                onClick = {  }
            ) {
                CustomTextView(
                    text = stringResource(id = R.string.bad),
                    color = Color.Black
                )
            }

        }
    }

}