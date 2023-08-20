/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.aayush.relabs.ui.components.BottomBar
import io.aayush.relabs.ui.navigation.Screen
import io.aayush.relabs.ui.navigation.SetupNavGraph
import io.aayush.relabs.ui.theme.ReLabsTheme
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""

        setContent {
            ReLabsTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = { BottomBar(navController = navController) }) {
                    SetupNavGraph(
                        navHostController = navController,
                        paddingValues = it,
                        if (accessToken.isNotBlank()) Screen.Home.route else Screen.Login.route
                    )
                }
            }
        }
    }
}
