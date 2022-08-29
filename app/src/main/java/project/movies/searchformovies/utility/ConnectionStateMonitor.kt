package project.movies.searchformovies.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class ConnectionStateMonitor(context: Context) : NetworkCallback() {
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    var lostNetwork: (() -> Unit)? = null

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun enable() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onLost(network: Network) {
        lostNetwork?.invoke()
    }
}