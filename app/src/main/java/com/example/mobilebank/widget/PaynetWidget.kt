package com.example.mobilebank.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.mobilebank.MainActivity
import com.example.mobilebank.R
import com.example.mobilebank.data.local.pref.MyPref
import com.example.mobilebank.data.model.Currency
import com.example.mobilebank.utils.getCurrentDate
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
class PaynetWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.paynet_widget)

    CoroutineScope(Dispatchers.Default).launch {
        val responseData = fetchDataFromNetwork()



        val usd = responseData.filter { it.Ccy == "USD" }
        val rub = responseData.filter { it.Ccy == "RUB" }
        val cny = responseData.filter { it.Ccy == "CNY" }

        views.setTextViewText(R.id.text_usa,  usd[0].Rate + " So'm")
        views.setTextViewText(R.id.text_ru, rub[0].Rate + " So'm")
        views.setTextViewText(R.id.text_china, cny[0].Rate + " So'm")

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    views.setTextViewText(R.id.text_date, getCurrentDate(System.currentTimeMillis()))

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.root, pendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private suspend fun fetchDataFromNetwork(): List<Currency> {
    return withContext(Dispatchers.IO) {
        val url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            responseData?.let {
                val gson = Gson()
                val currencies = gson.fromJson(it, Array<Currency>::class.java)
                currencies.toList()
            } ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}