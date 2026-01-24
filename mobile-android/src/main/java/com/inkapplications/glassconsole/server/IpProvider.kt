package com.inkapplications.glassconsole.server

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.net.Inet4Address
import java.net.NetworkInterface

/**
 * Provides information about the device's current network connections.
 */
class IpProvider(
    connectivityManager: ConnectivityManager,
    context: Context,
) {
    val currentIps = callbackFlow {
        trySend(getIPAddresses())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(getIPAddresses())
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(getIPAddresses())
                }
            }

            connectivityManager.registerDefaultNetworkCallback(networkCallback)

            awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
        } else {
            val networkChangeReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                        trySend(getIPAddresses())
                    }
                }
            }
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(networkChangeReceiver, intentFilter)

            awaitClose { context.unregisterReceiver(networkChangeReceiver) }
        }
    }

    private fun getIPAddresses(): List<String> {
        return NetworkInterface.getNetworkInterfaces()
            .toList()
            .flatMap {
                it.inetAddresses.toList()
                    .filterIsInstance<Inet4Address>()
                    .filter { !it.isLoopbackAddress }
                    .map { it.hostAddress }
            }
    }
}
