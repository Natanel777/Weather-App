package natanel.android.real_timeweatherapp.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.fragment.app.Fragment


//Check internet connection
fun Fragment.hasInternetConnection() : Boolean {
    val connectivityManager =  requireContext().getSystemService(ConnectivityManager::class.java)
    val network =  connectivityManager.activeNetwork
    return network?.let {
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } ?: false

}