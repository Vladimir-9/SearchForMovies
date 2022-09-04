package project.movies.searchformovies.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class ConnectionStateMonitor(context: Context) : NetworkCallback() {
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    private val _isNetworkLost = NonRepeatLiveData(false)
    val isNetworkLost: LiveData<Boolean> = _isNetworkLost

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun enable() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        _isNetworkLost.postValue(true)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _isNetworkLost.postValue(false)
    }
}