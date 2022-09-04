package project.movies.searchformovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.utility.ConnectionStateMonitor
import project.movies.searchformovies.utility.toast
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @JvmField
    var connectionState: ConnectionStateMonitor? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectionState?.isNetworkLost?.observe(this) { isLost ->
            if (isLost)
                toast(getString(R.string.no_internet))
        }
    }

    override fun onStart() {
        super.onStart()
        connectionState?.enable()
    }

    override fun onStop() {
        super.onStop()
        connectionState?.unregister()
    }
}