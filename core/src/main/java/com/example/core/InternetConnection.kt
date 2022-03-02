package com.example.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import javax.inject.Inject

interface InternetConnection {

    fun listen(listener: ConnectionListener)

    interface ConnectionListener {

        fun updateConnected(connected: Boolean)
    }

    class Base @Inject constructor(private val connectivityManager: ConnectivityManager) : InternetConnection {
//        private val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        override fun listen(listener: ConnectionListener) {
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo?.isConnected ?: false
            listener.updateConnected(isConnected)
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    return listener.updateConnected(true)
                }

                override fun onLost(network: Network) {
                    return listener.updateConnected(false)
                }
            }
        }
    }
}