package com.example.feature_error.domain

import com.example.core.ConnectResolver
import javax.inject.Inject

interface ErrorRepository {
    fun checkConnection(): Boolean

    class Base @Inject constructor(private val connectResolver: ConnectResolver) : ErrorRepository {
        override fun checkConnection(): Boolean {
            return connectResolver.isOnline()
        }
    }
}