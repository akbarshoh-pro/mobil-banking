package com.example.mobilebank.presentation.screens.map

import com.example.mobilebank.data.model.MarkerData
import org.orbitmvi.orbit.ContainerHost

interface MapContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        var ls: ArrayList<MarkerData>
    )

    sealed interface SideEffect {
        data class OpenBottomSheet(
            val data: MarkerData
        ) : SideEffect
    }

    sealed interface Intent {
        data object BackToProfile : Intent
        data class OpenBottomSheet(
            val data: MarkerData
        ) : Intent
        data object GetData : Intent
    }

}