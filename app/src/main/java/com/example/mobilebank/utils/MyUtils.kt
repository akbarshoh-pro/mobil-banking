package com.example.mobilebank.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.biometric.BiometricPrompt
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.fragment.app.FragmentActivity
import com.example.mobilebank.R
import com.example.mobilebank.data.local.entity.LastTransferUserEntity
import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.data.model.UserData
import com.example.mobilebank.data.remote.response.GetUserInfoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.Result.Companion.failure
import kotlin.experimental.ExperimentalTypeInference

fun formatDate(milliseconds: Long): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun LastTransferUserEntity.toUIData() =
    UserCardData(0, pan, owner)
fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        body()?.let {
            Result.success(it)
        } ?: failure(Exception("Response body is null!"))
    } else {
        failure(Exception("Error occurred: ${errorBody()?.string() ?: "Unknown error"}"))
    }
}

context(FlowCollector<Result<T>>)
suspend fun <T> Result<T>.emitWith() {
    emit(this)
}

@OptIn(ExperimentalTypeInference::class)
fun <T> safetyFlow(@BuilderInference block: suspend FlowCollector<Result<T>>.() -> Unit): Flow<Result<T>> =
    flow {
        block()
    }
        .flowOn(Dispatchers.IO)
        .catch { exception ->
            emit(failure(exception))
        }



fun checkColor(item: Int) : Color = if (item == 0) Color(0xFFF3F4FA) else Color(0xFF00AA00)

fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}

fun getCurrentLanguage(): String {
    return Locale.getDefault().language
}

fun maskPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 13 && phoneNumber.startsWith("+")) {
        return phoneNumber.substring(0, 4) + " " +
                phoneNumber.substring(4, 6) + " " +
                "••• •• " +
                phoneNumber.substring(11, 13)
    }
    return  ""
}

fun formatPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 13 && phoneNumber.startsWith("+")) {
        return phoneNumber.substring(0, 4) + " " +
                phoneNumber.substring(4, 6) + " " +
                phoneNumber.substring(6, 9) + " " +
                phoneNumber.substring(9, 11) + " " +
                phoneNumber.substring(11)
    }
    return ""
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun <T> Flow<T>.launch(scope: CoroutineScope, block: (T) -> Unit) {
    onEach { block(it) }
        .launchIn(scope)
}

fun dateFormatIsCorrect(date: String): Boolean {
    if (date.length != 4) return true
    val month = date.substring(0, 2).toIntOrNull() ?: return true
    val year = date.substring(2).toIntOrNull() ?: return true
    return !(month in 1..12 && year in 24..99)
}

fun hideCardNumbers(cardNumbers: String) : String {
    var newStr = ""
    if (cardNumbers.length == 16) {
        newStr += cardNumbers.substring(0, 4)
        newStr += " "
        newStr += cardNumbers.substring(4, 6)
        newStr += "** "
        newStr += "**** "
        newStr += cardNumbers.substring(12)
    }
    return newStr
}

fun moneyAmountIsCorrect(amount: String) : Boolean =
    amount.toInt() in 1000 .. 12400000

fun moneyFormat(value: String) : String {
    var newStr = ""
    if (value.length < 4) {
        newStr += value
    } else if (value.length in 4..6) {
        val index = value.length - 3
        newStr += value.substring(0, index) + " " + value.substring(index)
    } else if (value.length in 7..9) {
        val indexF = value.length - 6
        val indexS = value.length - 3
        newStr += value.substring(0, indexF) + " " + value.substring(indexF, indexS) + " " + value.substring(indexS)
    } else {
        newStr += "∞"
    }
    return newStr
}

fun moneyFormat2(value: String) : String {
    var newStr = ""
    if (value.length < 4) {
        newStr += value
    } else if (value.length in 4..6) {
        val index = value.length - 3
        newStr += value.substring(0, index) + "k"
    } else if (value.length in 7..9) {
        val indexF = value.length - 6
        newStr += value.substring(0, indexF) + "M"
    } else if (value.length in 10..12) {
        val indexF = value.length - 9
        newStr += value.substring(0, indexF) + "B"
    } else {
        newStr += "∞"
    }
    return newStr
}

fun getCardType(cardNumbers: String) : Int {
    return if (cardNumbers.startsWith("9860")) R.drawable.ic_paymentsystem_humo
    else if (cardNumbers.startsWith("8600")) R.drawable.ic_paymentsystem_uzcard
    else if (cardNumbers.startsWith("5440")) R.drawable.ic_paymentsystem_uzcard
    else if (cardNumbers.startsWith("5614")) R.drawable.ic_paymentsystem_uzcard
    else R.drawable.ic_paymentsystem_humo
}

/*
* else if (value.length in 10..12) {
        val indexF = value.length - 9
        val indexS = value.length - 6
        val indexT = value.length - 3
        newStr += value.substring(0, indexF) + " " + value.substring(indexF, indexS) + " " + value.substring(indexS, indexT) + " " + value.substring(indexT)
    }
* */

fun Context.requireBiometricAuth(
    onSuccess: (result: BiometricPrompt.AuthenticationResult) -> Unit,
    onFailed: () -> Unit,
    onError: (errorCode: Int, errString: CharSequence) -> Unit
) {
    val biometricAuthenticator = BiometricAuthenticator(this)

    biometricAuthenticator.promptBiometricAuth(
        title = "Barmoq izi bo'yicha avtorizatsiya",
        subTitle = "Verify identity\nTouch ID",
        negativeButtonText = "BEKOR QILISH",
        fragmentActivity = this as FragmentActivity,
        onSuccess = onSuccess,
        onError = onError,
        onFailed = onFailed,
    )
}

fun getGradient(type: Int) : Brush = when(type) {
    0 -> { Brush.verticalGradient(listOf(Color(0xFF0063B5), Color(0xFF00EBC8))) }
    1 -> { Brush.verticalGradient(listOf(Color(0xFF06693a), Color(0xFF20d970))) }
    2 -> { Brush.verticalGradient(listOf(Color(0xFF5b0a8a), Color(0xFFa9518d))) }
    3 -> { Brush.verticalGradient(listOf(Color(0xFF930709), Color(0xFFff9c63))) }
    4 -> { Brush.verticalGradient(listOf(Color(0xFF886e33), Color(0xFFffd645))) }
    5 -> { Brush.verticalGradient(listOf(Color(0xFF282a75), Color(0xFF009ffd))) }
    6 -> { Brush.verticalGradient(listOf(Color(0xFF191a1f), Color(0xFF55555f))) }
    7 -> { Brush.verticalGradient(listOf(Color(0xFF6c0f17), Color(0xFFbd1373))) }
    else -> { Brush.verticalGradient(listOf(Color(0xFFa95403), Color(0xFFecbe38))) }
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(time: Long): String {
    val date = Date(time)
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(date)
}
fun String.logger() = Timber.tag("TTT").d(this)

class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Keep only digits
        val digits = text.text.filter { it.isDigit() }
        val stringBuilder = StringBuilder()

        // Loop through the digits and insert spaces after every 4th digit
        digits.forEachIndexed { index, c ->
            stringBuilder.append(c)
            // Add space after every 4 digits, but not at the end
            if (index % 4 == 3 && index != digits.lastIndex) {
                stringBuilder.append(' ')
            }
        }

        val newText = stringBuilder.toString()

        // Create an offset mapping to correlate original text indexing with transformed text
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                if (offset > digits.length) return newText.length

                val spacesBeforeOffset = (offset - 1) / 4
                return offset + spacesBeforeOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return 0
                if (offset > newText.length) return digits.length

                val spacesBeforeOffset = (offset - 1) / 5
                return offset - spacesBeforeOffset
            }
        }

        return TransformedText(AnnotatedString(newText), offsetMapping)
    }
}


class ExpiryDateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val out = StringBuilder()
        for (i in text.text.indices) {
            if (i == 2 && text.text.length >= 2) {
                out.append('/')
            }
            out.append(text.text[i])
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = when {
                offset <= 2 -> offset
                offset > 2 -> offset + 1
                else -> offset
            }

            override fun transformedToOriginal(offset: Int): Int = when {
                offset <= 3 -> offset
                offset > 3 -> offset - 1
                else -> offset
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

class MoneyFormatTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val out = StringBuilder()
        var count = 0

        // Insert spaces from the right for every three digits
        for (i in originalText.indices.reversed()) {
            if (originalText[i].isDigit()) {
                out.insert(0, originalText[i])
                count++
                if (count % 3 == 0 && i != 0) {
                    out.insert(0, ' ')
                }
            } else {
                out.insert(0, originalText[i])
            }
        }

        val transformedText = out.toString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var extraSpaces = 0
                var digits = 0
                for (i in 0 until offset) {
                    if (originalText[i].isDigit()) {
                        digits++
                        if (digits % 3 == 0 && i != originalText.length - 1) {
                            extraSpaces++
                        }
                    }
                }
                return offset + extraSpaces
            }

            override fun transformedToOriginal(offset: Int): Int {
                var extraSpaces = 0
                var transOffset = offset
                while (transOffset > 0 && extraSpaces < offset) {
                    transOffset--
                    if (transformedText[transOffset] == ' ') {
                        extraSpaces++
                    }
                }
                return offset - extraSpaces
            }
        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }
        val stringBuilder = StringBuilder()
        for (i in digits.indices) {
            stringBuilder.append(digits[i])
            if (i == 1 || i == 4 || i == 6 || i == 8) {
                stringBuilder.append(' ')
            }
        }
        val newText = stringBuilder.toString().trim()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var countSpaces = 0
                for (i in 0 until offset.coerceAtMost(digits.length)) {
                    if (i == 2 || i == 5 || i == 7 || i == 9) {
                        countSpaces += 1
                    }
                }
                return (offset + countSpaces).coerceAtMost(newText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var countSpaces = 0
                var transformedIndex = 0
                for (i in newText.indices) {
                    if (newText[i] == ' ') countSpaces += 1
                    if (i == offset) break
                    transformedIndex++
                }
                return (transformedIndex - countSpaces).coerceAtLeast(0)
            }
        }

        return TransformedText(AnnotatedString(newText), offsetMapping)
    }
}

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 9) text.text.substring(0..8) else text.text
        val out = StringBuilder()

        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 || i == 3) out.append('/')
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 8) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                if (offset <= 10) return offset - 2
                return 8
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetTranslator)
    }
}

fun builderAnnotatedString(value: String, startWord: String, distance: Int) : AnnotatedString {
    return buildAnnotatedString {
        val startIndex = value.indexOf(startWord)
        val endIndex = startIndex + distance

        append(value)
        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun String.toMilliseconds(): Long {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date: Date = dateFormat.parse(this) as Date
    return date.time
}

fun GetUserInfoResponse.toUIData() =
    UserData(firstName, lastName, bornDate, phone)





//register
/*
* {
    "phone": "+998993946280",
    "password": "qwerty",
    "first-name": "Muhammadali",
    "last-name": "Rahimberganov",
    "born-date": "969822000000",
    "gender": "0"
}
* */

