package project.movies.searchformovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.utility.ConnectionStateMonitor
import project.movies.searchformovies.utility.toast
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectionState: ConnectionStateMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        connectionState.enable()
        connectionState.lostNetwork = {
            runOnUiThread {
                toast(getString(R.string.no_internet))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        connectionState.unregister()
    }
}