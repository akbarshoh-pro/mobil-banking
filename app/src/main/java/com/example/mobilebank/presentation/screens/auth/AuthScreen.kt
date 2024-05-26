package com.example.mobilebank.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.example.mobilebank.R
import com.example.mobilebank.presentation.dialog.ChangeLanguageDialog
import com.example.mobilebank.ui.components.CustomButton
import com.example.mobilebank.ui.components.CustomImageView
import com.example.mobilebank.ui.components.CustomTextField
import com.example.mobilebank.ui.components.CustomTextView
import com.example.mobilebank.ui.theme.btnNextDisable
import com.example.mobilebank.ui.theme.btnNextEnable
import com.example.mobilebank.ui.theme.colorInputBg
import com.example.mobilebank.ui.theme.textNextDisable
import com.example.mobilebank.ui.theme.textNextEnable
import com.example.mobilebank.utils.PhoneNumberVisualTransformation
import com.example.mobilebank.utils.builderAnnotatedString
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.openUrl
import com.example.mobilebank.utils.setLocale
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        val  bottomSheetNavigator = LocalBottomSheetNavigator.current
        val viewModel: AuthContract.ViewModel = getViewModel<AuthViewModel>()
        var lang by remember { mutableStateOf(getCurrentLanguage()) }
        val context = LocalContext.current

        viewModel.collectSideEffect { sideEffect ->
            when(sideEffect) {
                AuthContract.SideEffect.OpenChangeLanguageDialog -> {
                    bottomSheetNavigator.show(
                        ChangeLanguageDialog(
                            blockRu = {
                                setLocale(context, "ru")
                                lang = "ru"
                                bottomSheetNavigator.hide()
                            },
                            blockUz = {
                                setLocale(context, "uz")
                                lang = "uz"
                                bottomSheetNavigator.hide()
                            },
                            clickCancel = {
                                bottomSheetNavigator.hide()
                            }
                        )
                    )
                }
            }
        }

        if (lang != "") {
            AuthContent(
                uiState = viewModel.collectAsState(),
                onEventDispatcher = viewModel::onEventDispatcher
            )
        }
    }
}

@Composable
private fun AuthContent(uiState: State<AuthContract.UIState>, onEventDispatcher: (AuthContract.Intent) -> Unit) {
    val annotatedLinkRu = builderAnnotatedString(
        value = "Нажимая кнопку \"Продолжить\", я принимаю условия оферты о предоставлении услуг и даю согласие на обработку персональных данных",
        startWord = "оферты",
        distance = 23
    )

    val annotatedLinkUz = builderAnnotatedString(
        value = "\"Davom etish\" tugmasini bosish orgali men Xizmatlar ko'rsatish hagidagi oferta shartlarini qabul qilaman va shaxsiy malumotlarni qayta ishlashga rozilik bildiraman",
        startWord = "Xizmatlar",
        distance = 48
    )

    var number by remember { mutableStateOf("") }
    val noRippleInteractionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val url = "https://assets-global.website-files.com/63a7038e6eb0c1f38cd4d11f/659e7e324fed06afd1dbacfb_%D0%BE%D1%84%D0%B5%D1%80%D1%82%D0%B0%20%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D0%BA%D0%B0%202024.pdf"

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomImageView(
                src =  R.drawable.app_logo,
                modifier = Modifier
                    .size(72.dp)
            )

            CustomTextView(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(id = R.string.enter_phone),
                fontWeight = 800,
                fontSize = 24
            )

            CustomTextView(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = stringResource(id = R.string.for_client),
                fontWeight = 400,
                fontSize = 14
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp, end = 24.dp)
                .height(48.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    indication = null,
                    interactionSource = noRippleInteractionSource
                ) { onEventDispatcher(AuthContract.Intent.OpenChangeLanguageDialog) }
                .background(color = colorInputBg, shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CustomTextView(
                modifier = Modifier
                    .padding(start = 14.dp, end = 6.dp),
                text = stringResource(id = R.string.language),
                fontSize = 18,
                fontWeight = 600
            )

            Box(
                modifier = Modifier
                    .padding(end = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomImageView(
                    src = if (getCurrentLanguage() == "ru") R.drawable.ru else  R.drawable.uz
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 270.dp)
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.TopCenter)
                .background(color = colorInputBg, shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.44F)
            ) {

                CustomImageView(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterStart),
                    src = R.drawable.uz
                )

                Icon(
                    modifier = Modifier
                        .padding(start = 64.dp)
                        .size(24.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = null
                )

                CustomTextView(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    text = "+ 998",
                    fontSize = 18,
                    fontWeight = 600
                )
            }

            CustomTextField(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.56F),
                value = number,
                fontSize = 18,
                fontWeight = 600,
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && it.length <= 9) {
                        number = it
                    }
                },
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                visualTransformation = PhoneNumberVisualTransformation(),
                keyboardActions = KeyboardActions.Default
            )
        }

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            CustomButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                text = stringResource(id = R.string.continue_btn),
                fontSize = 20,
                fontWeight = 700,
                textColor = if (number.length == 9) textNextEnable else textNextDisable,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = btnNextDisable,
                    disabledContentColor = textNextDisable,
                    contentColor = textNextEnable,
                    containerColor = btnNextEnable
                ),
                enabled = number.length == 9,
                click = { onEventDispatcher(AuthContract.Intent.RegisterOrCreate(phone = "+998$number")) }
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = noRippleInteractionSource
                    ) { openUrl(context, url) }
                    .padding(horizontal = 16.dp),
                text = if (getCurrentLanguage() == "ru") annotatedLinkRu else annotatedLinkUz,
                fontSize =  if (getCurrentLanguage() == "ru") 15.sp else 16.sp,
                fontFamily = FontFamily(Font(R.font.pnfont_semibold)),
                textAlign = TextAlign.Center,
                color = Color(0xFF8B8A8A),
                fontWeight = FontWeight(200),
            )
        }

    }

}

