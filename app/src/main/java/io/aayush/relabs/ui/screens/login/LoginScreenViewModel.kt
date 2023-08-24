package io.aayush.relabs.ui.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // false positive, see https://github.com/google/dagger/issues/3253
class LoginScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        // Taken from official XDA app for oauth2 login
        private const val AUTHORIZATION_URL = "https://forum.xda-developers.com/audapi/oauth2/authorize"
        private const val TOKEN_URL = "https://forum.xda-developers.com/api/audapi-oauth2/token"
        private const val CLIENT_ID = "com.xda.labs.play"
        private const val REDIRECT_URI = "com.xda.labs.play.native://--/"
        private const val SCOPE = "audapp:user"
    }

    val authorizationService = AuthorizationService(context)
    private val authorizationServiceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AUTHORIZATION_URL),
        Uri.parse(TOKEN_URL)
    )
    val authState = AuthState(authorizationServiceConfiguration)
    val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "")

    fun getAuthReqIntent(): Intent {
        val authRequestBuilder = AuthorizationRequest.Builder(
            authorizationServiceConfiguration,
            CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(REDIRECT_URI)
        )

        authRequestBuilder.setScope(SCOPE)
        return authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
    }

    fun saveAccessToken() {
        sharedPreferences.edit(true) { putString(ACCESS_TOKEN, authState.accessToken) }
    }
}
