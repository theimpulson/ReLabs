/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import io.aayush.relabs.ui.navigation.SetupNavGraph
import io.aayush.relabs.ui.theme.ReLabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReLabsTheme {
                val navController = rememberNavController()
                SetupNavGraph(navHostController = navController)
            }
        }
    }
}
