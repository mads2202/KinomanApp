package com.mads2202.kinomanapp.util.networkUtil

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper(val context: Context) {
    fun isNetworkConnected(): Boolean {
        var result = false
        //connectivityManager класс который следит за тем подключено ли устройство к интернету
        // и оповещает об этом приложение
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //Если версия андройд больше или равна 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //получаем текущую активную сеть или возвращаем false если она null
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            //возвращаем возможности сети для данной сети которую получили выше или если null
            // возвращаем false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                //возвращает детальную информацию о активной сети устарело, заменили на
                //connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}
