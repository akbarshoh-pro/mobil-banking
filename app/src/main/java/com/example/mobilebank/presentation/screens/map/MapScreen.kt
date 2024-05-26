package com.example.mobilebank.presentation.screens.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.presentation.dialog.LocationDialog
import com.example.mobilebank.ui.components.CustomAppBar
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.utils.logger
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MapScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MapContract.ViewModel = getViewModel<MapViewModel>()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        viewModel.onEventDispatcher(MapContract.Intent.GetData)

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                is MapContract.SideEffect.OpenBottomSheet -> {
                    bottomSheetNavigator.show(
                        LocationDialog(sideEffect.data)
                    )
                }
            }
        }
        MapContent(
            uiState = viewModel.collectAsState().value,
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
    @Composable
    fun MapContent(
        uiState: MapContract.UIState,
        onEventDispatcher: (MapContract.Intent) -> Unit
    ) {
        val myCameraPosition = rememberCameraPositionState {
            val latLng = LatLng(41.31, 69.31) // Tashkent
            position = CameraPosition.fromLatLngZoom(latLng, 10f)
        }

        CustomAppBar(
            title = stringResource(id = R.string.map),
            onClick = { onEventDispatcher(MapContract.Intent.BackToProfile) }
        ) {
            GoogleMap(
                modifier = Modifier
                    .padding(it),
                cameraPositionState = myCameraPosition,
                properties = MapProperties(),
                uiSettings = MapUiSettings(compassEnabled = true, zoomControlsEnabled = false, myLocationButtonEnabled = true)
            ) {
                uiState.ls.forEach { mData ->
                    mData.location.logger()
                    Marker(
                        state = MarkerState(position = LatLng(mData.lat, mData.lng)),
                        onClick = {
                            onEventDispatcher(MapContract.Intent.OpenBottomSheet(mData))
                            true
                        },
                        title = mData.phone,
                    )
                }
            }
        }
    }
}