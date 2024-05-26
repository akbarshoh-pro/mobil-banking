package com.example.mobilebank.presentation.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.mobilebank.presentation.screens.main.pages.history.HistoryScreen
import com.example.mobilebank.presentation.screens.main.pages.home.HomeScreen
import com.example.mobilebank.presentation.screens.main.pages.payment.PaymentScreen
import com.example.mobilebank.presentation.screens.main.pages.transfer.TransferScreen
import com.example.mobilebank.ui.theme.selectedItemColor
import com.example.mobilebank.ui.theme.unSelectedItemColor
import com.example.mobilebank.utils.MyDataLoader

class MainScreen : Screen {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        TabNavigator(HomeScreen) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeScreen)
                        TabNavigationItem(TransferScreen)
                        TabNavigationItem(PaymentScreen)
                        TabNavigationItem(HistoryScreen)
                    }
                }
            )
        }
    }

}
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val interactionSource = remember { MutableInteractionSource() }

    BottomNavigationItem(
        modifier = Modifier
            .background(color = Color.White),
        selectedContentColor = selectedItemColor,
        unselectedContentColor = unSelectedItemColor,
        label = { Text(text = tab.options.title)},
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } },
        interactionSource = interactionSource,
        alwaysShowLabel = true
    )
}

