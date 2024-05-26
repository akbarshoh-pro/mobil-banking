package com.example.mobilebank.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilebank.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    fontSize: Int = 20,
    containerColor: Color = Color.White,
    titleContentColor: Color = Color.Black,
    actionIconContentColor: Color = Color.Black,
    titleMargin: PaddingValues = PaddingValues(start = 16.dp),
    iconMargin: PaddingValues = PaddingValues(start = 16.dp),
    onClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = containerColor),
                title = {
                    CustomTextView(
                        modifier = Modifier
                            .padding(titleMargin),
                        text = title,
                        fontSize = fontSize,
                        color = titleContentColor
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(iconMargin)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onClick() },
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = actionIconContentColor
                    )
                }
            )
        },
        content = content
    )
}
