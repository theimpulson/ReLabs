/*
 * SPDX-FileCopyrightText: 2023 Aayush Gupta
 * SPDX-License-Identifier: Apache-2.0
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("com.google.relay") version "0.3.08" apply false
}
