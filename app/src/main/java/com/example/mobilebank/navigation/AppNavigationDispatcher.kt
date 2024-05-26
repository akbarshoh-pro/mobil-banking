package com.example.mobilebank.navigation

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {
    override val uiNavigator = MutableSharedFlow<NavigationArgs>()

    private suspend fun mNavigate(navigator: NavigationArgs) {
        uiNavigator.emit(navigator)
    }

    override suspend fun addScreen(screen: Screen) = mNavigate {
        push(screen)
    }

    override suspend fun replaceScreen(screen: Screen) = mNavigate {
        replace(screen)
    }

    override suspend fun replaceAll(screen: Screen) = mNavigate {
        replaceAll(screen)
    }

    override suspend fun back() = mNavigate {
        pop()
    }

    override suspend fun backUntil(screen: Screen) = mNavigate {
        popUntil { it == screen }
    }

    override suspend fun backToRoot() = mNavigate {
        popUntilRoot()
    }

}