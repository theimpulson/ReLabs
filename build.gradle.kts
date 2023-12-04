/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("com.google.relay") version "0.3.09" apply false
}
