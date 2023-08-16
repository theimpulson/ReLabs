/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.login

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.aayush.relabs.R
import io.aayush.relabs.ui.components.LoginButton
import io.aayush.relabs.ui.navigation.Screen
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues

private const val TAG = "LoginScreen"

// Taken from official XDA app for oauth2 login
private const val AUTHORIZATION_URL = "https://forum.xda-developers.com/audapi/oauth2/authorize"
private const val TOKEN_URL = "https://forum.xda-developers.com/api/audapi-oauth2/token"
private const val CLIENT_ID = "com.xda.labs.play"
private const val REDIRECT_URI = "com.xda.labs.play.native://--/"
private const val SCOPE = "audapp:user"

private lateinit var authState: AuthState

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val authorizationService = AuthorizationService(LocalContext.current)

    val startActivityForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            try {
                val response = AuthorizationResponse.fromIntent(it.data!!)
                val exception = AuthorizationException.fromIntent(it.data!!)
                authState.update(response, exception)

                authorizationService.performTokenRequest(
                    response!!.createTokenExchangeRequest()
                ) { res, ex ->
                    Log.i(TAG, "Logged in!")
                    authState.update(res, ex)
                    navHostController.navigate(Screen.Home.route)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "Failed to log in", exception)
            }
        }
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 80.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.welcome),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.sign_in_hint),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LoginButton {
                if (!::authState.isInitialized) {
                    startActivityForResult.launch(getAuthReqIntent(authorizationService))
                }
            }
        }
    }
}

private fun getAuthReqIntent(authorizationService: AuthorizationService): Intent {
    val authorizationServiceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AUTHORIZATION_URL),
        Uri.parse(TOKEN_URL)
    )

    authState = AuthState(authorizationServiceConfiguration)

    val authRequestBuilder = AuthorizationRequest.Builder(
        authorizationServiceConfiguration,
        CLIENT_ID,
        ResponseTypeValues.CODE,
        Uri.parse(REDIRECT_URI)
    )

    authRequestBuilder.setScope(SCOPE)
    return authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
}

@Composable
@Preview
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}
