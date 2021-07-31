package project.movies.searchformovies.connectivity_info

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Parcelable
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import project.movies.searchformovies.R

class NetworkChangeReceiver : BroadcastReceiver() {

    private var isOneQuery = true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val currentStateNetwork = stateNetwork(context)

        if (currentStateNetwork == null && isOneQuery) {
            createToast(context)
            isOneQuery = false
        } else {
            isOneQuery = true
        }
    }

    private fun stateNetwork(context: Context): Parcelable? {
        val connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        return if (Build.VERSION_CODES.M >= Build.VERSION.SDK_INT)
            connectivityManager?.activeNetworkInfo
        else
            connectivityManager?.activeNetwork
    }

    private fun createToast(context: Context) {
        val toast = Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 200)
        toast.show()
    }
}