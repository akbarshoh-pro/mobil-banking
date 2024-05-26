package com.example.mobilebank.presentation.screens.main.pages.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.mobilebank.R
import com.example.mobilebank.data.remote.response.Child
import com.example.mobilebank.presentation.screens.main.pages.home.HomeContract
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.textColor
import com.example.mobilebank.utils.logger

object HistoryScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.history)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_clock))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
    @Composable
    override fun Content() {
        val viewModel: HistoryContract.ViewModel = getViewModel<HistoryViewModel>()
        HistoryContent(
            ls = viewModel.getCardHistory().collectAsLazyPagingItems()
        )
    }
}

@Composable
private fun HistoryContent(
    ls: LazyPagingItems<Child>
) {
    "size -> ${ls.itemCount}".logger()
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            CustomTextView(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                text = stringResource(id = R.string.payment),
                fontSize = 28
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 56.dp, bottom = 56.dp)
        ) {
            items(ls.itemCount) {
                ls[it]?.let { data ->
                    if (data.type == "income") {
                        HistoryIncomeTransactionItem(
                            data = data
                        ) {

                        }
                    } else {
                        HistoryOutcomeTransactionItem(
                            data = data
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryOutcomeTransactionItem(
    data: Child,
    modifier: Modifier = Modifier, onClickItem: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) { onClickItem() },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_operations_arrow_up),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFFD8D7D7))
                        .size(46.dp)
                        .padding(8.dp)

                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        CustomTextView(
                            text = data.to,
                            fontSize = 16,
                            color = Color.Black)
                        Spacer(modifier = Modifier.weight(1f))
                        CustomTextView(
                            text = "${data.amount} so'm",
                            fontSize = 16,
                            color = Color.Black
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)) {
                        CustomTextView(
                            text = if (data.type == "income") "Kiruvchi o'tkazma" else "Chiquvchi o'tkazma",
                            color = textColor,
                            fontSize = 12,
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_operations_credit_card),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )


                        CustomTextView(
                            text = if (data.from.length >= 16) " • ${data.from.substring(12)}" else data.from,
                            fontSize = 12,
                            color = Color.Black)
                    }
                }

            }
        }
    }
}

@Composable
fun HistoryIncomeTransactionItem(
    data: Child,
    modifier: Modifier = Modifier, onClickItem: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) { onClickItem() },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_operations_arrow_up),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFFD8D7D7))
                        .size(46.dp)
                        .padding(8.dp)
                        .rotate(180f)

                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        CustomTextView(
                            text = data.from,
                            fontSize = 16,
                            color = Color.Black)
                        Spacer(modifier = Modifier.weight(1f))
                        CustomTextView(text = "${data.amount} so'm", fontSize = 16, color = Color.Black)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)) {
                        CustomTextView(
                            text = if (data.type == "income") "Kiruvchi o'tkazma" else "Chiquvchi o'tkazma",
                            color = textColor,
                            fontSize = 12,
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_operations_credit_card),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )

                        CustomTextView(
                            text = if (data.to.length >= 16) " • ${data.to.substring(12)}" else data.to,
                            fontSize = 12,
                            color = Color.Black)
                    }
                }

            }
        }
    }
}