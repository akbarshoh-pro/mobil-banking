package com.example.mobilebank.presentation.screens.map

import androidx.lifecycle.ViewModel
import com.example.mobilebank.data.model.MarkerData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val directions: MapDirections
) : ViewModel(), MapContract.ViewModel {
    override val container = container<MapContract.UIState, MapContract.SideEffect>(MapContract.UIState(
        arrayListOf()
    ))
    private val ls = arrayListOf<MarkerData>()
    init {
        ls.add(MarkerData(
                lat = 41.3327122,
                lng = 69.3222064,
                location = "88WW+R59, Trotuar, Tashkent, Toshkent Shahri, Узбекистан",
                phone = "+998998771125"
            ))

        ls.add(MarkerData(
            lat = 41.2953108,
            lng = 69.2387671,
            location = "Oqqo’rg’on ko'chasi 14, Тоshkent, Toshkent, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.2961185,
            lng = 69.2418713,
            location = "879W+QH9, Tashkent, Toshkent Shahri, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.2961185,
            lng = 69.2418713,
            location = "Kari Niyazov ko'chasi 10, Тоshkent, Toshkent, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.2821794,
            lng = 69.1239123,
            location = "Abdulla Qodiriy ko'chasi 36, Тоshkent, Toshkent, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.2863881,
            lng = 69.1570555,
            location = "Furqat ko'chasi 10, 100021, Тоshkent, Toshkent, Узбекистан",
            phone = "+998712020707"
        ))

        ls.add(MarkerData(
            lat = 41.2859125,
            lng = 69.1684802,
            location = "Chehov ko'chasi 33, Тоshkent, Toshkent, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.1833988,
            lng = 69.0340817,
            location = "75RV+M3P, 100173, Tashkent, Toshkent Shahri, Узбекистан",
            phone = "+998998771125"
        ))

        ls.add(MarkerData(
            lat = 41.282015182125335,
            lng = 69.25252027555989,
            location = "Muqimiy ko'chasi 59, Тоshkent, Toshkent, Узбекистан",
            phone = "+998712020707"
        ))

        ls.add(MarkerData(
            lat = 41.2022615,
            lng = 69.0393789,
            location = "666F+PGG, 100012, Tashkent, Toshkent Shahri, Узбекистан",
            phone = "+998998771125"
        ))
    }
    override fun onEventDispatcher(intent: MapContract.Intent) = intent {
        when(intent) {
            MapContract.Intent.BackToProfile -> {
                directions.backProfileScreen()
            }
            MapContract.Intent.GetData -> {
                container.stateFlow.value.ls = ls
            }
            is MapContract.Intent.OpenBottomSheet -> {
                postSideEffect(MapContract.SideEffect.OpenBottomSheet(intent.data))
            }
        }
    }

}