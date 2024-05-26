package com.example.mobilebank.presentation.dialog

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
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView

class AddCardDialog (
    val blockUz: () -> Unit = {},
    val blockRu: () -> Unit = {},
    val blockCancel: () -> Unit = {}
) : Screen {
    @Composable
    override fun Content() {
        AddCardDialogContent(
            blockUz = blockUz,
            blockRu = blockRu,
            blockCancel = blockCancel
        )
    }

    @Composable
    private fun AddCardDialogContent(
        blockUz: () -> Unit = {},
        blockRu: () -> Unit = {},
        blockCancel: () -> Unit = {}
    ) {
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
                        .padding(top = 12.dp)
                        .align(Alignment.TopEnd)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) { blockCancel() }
                        .background(color = Color(0xFFD6D5D5), shape = RoundedCornerShape(100))
                        .size(32.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color(0xFF616060)
                    )
                }
            }

            CustomTextView(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = stringResource(id = R.string.how_type_card_add),
                fontSize = 22,
                textAlign = TextAlign.Left
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 36.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { blockUz() }
                    .fillMaxWidth()
            ) {
                CustomImageView(
                    modifier = Modifier
                        .size(40.dp),
                    src = R.drawable.uz
                )
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 56.dp),
                    text = stringResource(id = R.string.add_uz_card),
                    textAlign = TextAlign.Left,
                    fontSize = 18
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) { blockRu() }
                    .fillMaxWidth()
            ) {
                CustomImageView(
                    modifier = Modifier
                        .size(40.dp),
                    src = R.drawable.ru
                )
                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 56.dp),
                    text = stringResource(id = R.string.add_ru_card),
                    textAlign = TextAlign.Left,
                    fontSize = 18
                )
            }

        }
    }

}