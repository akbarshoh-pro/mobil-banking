package com.example.mobilebank.presentation.screens.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.mobilebank.R
import com.example.mobilebank.ui.components.CustomAppBar
import org.orbitmvi.orbit.compose.collectAsState

class NotificationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: NotificationContract.ViewModel = getViewModel<NotificationViewModel>()
        NotificationContent(
            uiState = viewModel.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@Composable
private fun NotificationContent(
    uiState: State<NotificationContract.UIState>,
    onEventDispatcher: (NotificationContract.Intent) -> Unit
) {
    CustomAppBar(
        title = stringResource(id = R.string.notification),
        onClick = { onEventDispatcher(NotificationContract.Intent.BackMainScreen) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        )
    }
}