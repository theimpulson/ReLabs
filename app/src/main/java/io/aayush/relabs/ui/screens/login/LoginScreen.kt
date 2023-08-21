/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.login

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.ui.components.LoginButton
import io.aayush.relabs.ui.navigation.Screen
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        if (!viewModel.accessToken.isNullOrEmpty()) {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    val startActivityForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            try {
                val response = AuthorizationResponse.fromIntent(it.data!!)
                val exception = AuthorizationException.fromIntent(it.data!!)
                viewModel.authState.update(response, exception)

                viewModel.authorizationService.performTokenRequest(
                    response!!.createTokenExchangeRequest()
                ) { res, ex ->
                    Log.i(TAG, "Logged in!")

                    // Save AccessToken
                    viewModel.authState.update(res, ex)
                    viewModel.saveAccessToken()

                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                startActivityForResult.launch(viewModel.getAuthReqIntent())
            }
        }
    }
}
