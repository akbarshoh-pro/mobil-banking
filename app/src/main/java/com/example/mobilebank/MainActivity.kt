package com.example.mobilebank

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.example.mobilebank.data.local.pref.MyPref
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.data.remote.ConnectivityLiveData
import com.example.mobilebank.domain.usecase.GetAllCardsUseCase
import com.example.mobilebank.domain.usecase.GetUserInfoUseCase
import com.example.mobilebank.navigation.AppNavigationDispatcher
import com.example.mobilebank.navigation.AppNavigationHandler
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.no_connection.NoConnectionScreen
import com.example.mobilebank.presentation.screens.splash.SplashScreen
import com.example.mobilebank.presentation.screens.transfer_card.TransferCardScreen
import com.example.mobilebank.ui.theme.MobileBankTheme
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.NetworkStatusValidator
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.setLocale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var handler: AppNavigationHandler

    @Inject
    lateinit var pref: MyPref

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    lateinit var networkStatusValidator: NetworkStatusValidator

    @Inject
    lateinit var allCardsUseCase: GetAllCardsUseCase
    @Inject
    lateinit var userInfoUseCase: GetUserInfoUseCase

    @Inject
    lateinit var dispatcher: AppNavigationDispatcher

    private var time = 0L
    object NetworkStatus {
        val hasNetwork = MutableStateFlow(true)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyDataLoader.init(allCardsUseCase, userInfoUseCase)


        setContent {
            MobileBankTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color(0xFF000000))

                Surface(
                    color = Color.White
                ) {
                    BottomSheetNavigator(
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ) {
                        Navigator(screen = SplashScreen()) { navigator ->
                            networkStatusValidator.init({})
                            handler.uiNavigator
                                .onEach { it.invoke(navigator) }
                                .launchIn(lifecycleScope)
                            CurrentScreen()
                        }

                        NetworkStatus.hasNetwork.onEach {
                            if (!it) dispatcher.addScreen(NoConnectionScreen())
                        }.launchIn(lifecycleScope)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setLocale(this, pref.appLanguage())
        val connectivityLiveData = ConnectivityLiveData(this)

        connectivityLiveData.observe(this) { networkState ->
            if (networkState.isConnected) {
                NetworkStatus.hasNetwork.tryEmit(true)

            } else {
                NetworkStatus.hasNetwork.tryEmit(false)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        pref.changeLanguage(getCurrentLanguage())
        time = System.currentTimeMillis()
    }
}





















