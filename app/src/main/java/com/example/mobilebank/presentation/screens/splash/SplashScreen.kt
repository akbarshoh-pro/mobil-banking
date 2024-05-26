package com.example.mobilebank.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mobilebank.R

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SplashContract.ViewModel = getViewModel<SplashViewModel>()
        SplashContent(viewModel::onEventDispatcher)
    }
}

@Composable
private fun SplashContent(
    onEventDispatcher: (SplashContract.Intent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))

            val preloaderProgress by animateLottieCompositionAsState(
                preloaderLottieComposition,
                isPlaying = true
            )

            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            LaunchedEffect(preloaderProgress) {
                if (preloaderProgress == 1f) {
                    onEventDispatcher(SplashContract.Intent.NavigateTo)
                }
            }

        }
    }

}