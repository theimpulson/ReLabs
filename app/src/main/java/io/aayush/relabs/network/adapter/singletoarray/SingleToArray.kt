package io.aayush.relabs.network.adapter.singletoarray

import com.squareup.moshi.JsonQualifier

// Taken from: https://stackoverflow.com/q/53344033/8446131
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@JsonQualifier
annotation class SingleToArray
