package com.example.mobilebank.di

import com.example.mobilebank.presentation.screens.add_card.AddCardDirections
import com.example.mobilebank.presentation.screens.add_card.AddCardDirectionsImp
import com.example.mobilebank.presentation.screens.auth.AuthDirections
import com.example.mobilebank.presentation.screens.auth.AuthDirectionsImp
import com.example.mobilebank.presentation.screens.card_details.CardDetailsDirections
import com.example.mobilebank.presentation.screens.card_details.CardDetailsDirectionsImp
import com.example.mobilebank.presentation.screens.card_theme.CardThemeDirections
import com.example.mobilebank.presentation.screens.card_theme.CardThemeDirectionsImp
import com.example.mobilebank.presentation.screens.confirm.ConfirmPinDirections
import com.example.mobilebank.presentation.screens.confirm.ConfirmPinDirectionsImp
import com.example.mobilebank.presentation.screens.create.CreatePinDirections
import com.example.mobilebank.presentation.screens.create.CreatePinDirectionsImp
import com.example.mobilebank.presentation.screens.daily.DailyPinDirections
import com.example.mobilebank.presentation.screens.daily.DailyPinDirectionsImp
import com.example.mobilebank.presentation.screens.map.MapDirections
import com.example.mobilebank.presentation.screens.map.MapDirectionsImp
import com.example.mobilebank.presentation.screens.my_cards.MyCardsDirections
import com.example.mobilebank.presentation.screens.my_cards.MyCardsDirectionsImp
import com.example.mobilebank.presentation.screens.notification.NotificationDirections
import com.example.mobilebank.presentation.screens.notification.NotificationDirectionsImp
import com.example.mobilebank.presentation.screens.main.pages.home.HomeDirections
import com.example.mobilebank.presentation.screens.main.pages.home.HomeDirectionsImp
import com.example.mobilebank.presentation.screens.main.pages.transfer.TransferDirections
import com.example.mobilebank.presentation.screens.main.pages.transfer.TransferDirectionsImp
import com.example.mobilebank.presentation.screens.profile.ProfileDirections
import com.example.mobilebank.presentation.screens.profile.ProfileDirectionsImp
import com.example.mobilebank.presentation.screens.settings.SettingsDirections
import com.example.mobilebank.presentation.screens.settings.SettingsDirectionsImp
import com.example.mobilebank.presentation.screens.sms.SmsDirections
import com.example.mobilebank.presentation.screens.sms.SmsDirectionsImp
import com.example.mobilebank.presentation.screens.splash.SplashDirections
import com.example.mobilebank.presentation.screens.splash.SplashDirectionsImp
import com.example.mobilebank.presentation.screens.to_mycard.TransferMyCardDirections
import com.example.mobilebank.presentation.screens.to_mycard.TransferMyCardDirectionsImp
import com.example.mobilebank.presentation.screens.transfer_card.TransferCardDirections
import com.example.mobilebank.presentation.screens.transfer_card.TransferCardDirectionsImp
import com.example.mobilebank.presentation.screens.transfer_success.TransferSuccessDirections
import com.example.mobilebank.presentation.screens.transfer_success.TransferSuccessDirectionsImp
import com.example.mobilebank.presentation.screens.transfer_verify.TransferVerifyDirections
import com.example.mobilebank.presentation.screens.transfer_verify.TransferVerifyDirectionsImp
import com.example.mobilebank.presentation.screens.update_data.UpdateDataDirections
import com.example.mobilebank.presentation.screens.update_data.UpdateDataDirectionsImp
import com.example.mobilebank.presentation.screens.what_is.WhatIsDirections
import com.example.mobilebank.presentation.screens.what_is.WhatIsDirectionsImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DirectionsModule {
    @Binds
    fun splashDirections(directionsImp: SplashDirectionsImp): SplashDirections

    @Binds
    fun authDirections(directionsImp: AuthDirectionsImp): AuthDirections

    @Binds
    fun smsDirections(directionsImp: SmsDirectionsImp): SmsDirections

    @Binds
    fun createDirections(directionsImp: CreatePinDirectionsImp): CreatePinDirections

    @Binds
    fun confirmDirections(directionsImp: ConfirmPinDirectionsImp): ConfirmPinDirections

    @Binds
    fun dailyDirections(directionsImp: DailyPinDirectionsImp): DailyPinDirections

    @Binds
    fun homeDirections(directionsImp: HomeDirectionsImp): HomeDirections

    @Binds
    fun transferDirections(directionsImp: TransferDirectionsImp): TransferDirections

    @Binds
    fun profileDirections(directionsImp: ProfileDirectionsImp): ProfileDirections
    @Binds
    fun notificationDirections(directionsImp: NotificationDirectionsImp): NotificationDirections

    @Binds
    fun mapDirections(directionsImp: MapDirectionsImp): MapDirections

    @Binds
    fun addCardDirections(directionsImp: AddCardDirectionsImp): AddCardDirections

    @Binds
    fun cardDetailsDirections(directionsImp: CardDetailsDirectionsImp): CardDetailsDirections

    @Binds
    fun myCardsDirections(directionsImp: MyCardsDirectionsImp): MyCardsDirections

    @Binds
    fun settingsDirections(directionsImp: SettingsDirectionsImp): SettingsDirections

    @Binds
    fun whatIsDirections(directionsImp: WhatIsDirectionsImp): WhatIsDirections

    @Binds
    fun transferCardDirections(directionsImp: TransferCardDirectionsImp): TransferCardDirections

    @Binds
    fun transferVerifyDirections(directionsImp: TransferVerifyDirectionsImp): TransferVerifyDirections

    @Binds
    fun transferSuccessDirections(directionsImp: TransferSuccessDirectionsImp): TransferSuccessDirections

    @Binds
    fun cardThemeDirections(directionsImp: CardThemeDirectionsImp): CardThemeDirections

    @Binds
    fun transferMyCardDirections(directionsImp: TransferMyCardDirectionsImp): TransferMyCardDirections

    @Binds
    fun updateDataDirections(directionsImp: UpdateDataDirectionsImp) : UpdateDataDirections
}