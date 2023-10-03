package com.example.truecallerdemo


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class CommonUtils {

    companion object {

        fun getConnectivityStatus(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.state == NetworkInfo.State.CONNECTED
        }

    }
}