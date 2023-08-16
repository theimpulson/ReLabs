/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

package io.aayush.relabs.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {}
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
