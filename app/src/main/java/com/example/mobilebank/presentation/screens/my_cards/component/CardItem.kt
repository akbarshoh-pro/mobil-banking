package com.example.mobilebank.presentation.screens.my_cards.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobilebank.R
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.utils.getCardType
import com.example.mobilebank.utils.moneyFormat

@Composable
internal fun CardItem(modifier: Modifier = Modifier, data: CardData, gradient: Brush, block: (CardData) -> Unit) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(220.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) { block(data) }
            .fillMaxWidth()
    ) {

        if (getCardType(data.pan) == R.drawable.ic_paymentsystem_humo) {
            CustomImageView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .width(160.dp)
                    .height(50.dp),
                src = getCardType(data.pan)
            )
        }
        else {
            CustomImageView(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 24.dp)
                    .size(90.dp),
                src = getCardType(data.pan)
            )
        }

        if (getCardType(data.pan) == R.drawable.ic_paymentsystem_humo) {
            CustomTextView(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp),
                text = "Humo",
                color = Color.White,
                fontSize = 20
            )
        }
        else {
            CustomTextView(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp),
                text = "Uzcard",
                color = Color.White,
                fontSize = 20
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            CustomTextView(
                text = moneyFormat(data.amount.toString()),
                fontSize = 28,
                color = Color.White,
                fontWeight = 700
            )
            CustomTextView(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = stringResource(id = R.string.money),
                fontSize = 24,
                color = Color(0x9CFFFFFF),
                fontWeight = 600
            )
        }

        CustomTextView(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 62.dp, start = 16.dp),
            text = "**** **** **** ${data.pan}",
            color = Color.White,
            fontSize = 18,
            fontWeight = 20
        )

        CustomTextView(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(top = 56.dp, end = 16.dp),
            text = if(data.expiredMonth < 10) "0${data.expiredMonth}/${data.expiredYear.toString().substring(2)}" else "${data.expiredMonth}/${data.expiredYear.toString().substring(2)}",
            color = Color.White,
            fontSize = 16,
            fontWeight = 20
        )

    }
}