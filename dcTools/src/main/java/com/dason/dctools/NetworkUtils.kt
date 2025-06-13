package com.dason.dctools

import android.content.Context
import android.net.ConnectivityManager
import java.net.NetworkInterface

object NetworkUtils {
    val TAG: String = NetworkUtils::class.java.simpleName
    const val NET_WORK_NONE: Int = 0 //无网络
    const val NET_WORK_WLAN: Int = 1 //数据网络
    const val NET_WORK_WIFI: Int = 2 //Wifi网络

    /**
     * 判断是否有网络
     */
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (EmptyUtils.isEmpty(connectivity)) {
            return false
        }
        val networkInfos = connectivity.allNetworkInfo
        if (EmptyUtils.isEmpty(networkInfos)) {
            return false
        }
        for (networkInfo in networkInfos) {
            if (networkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     * @throws Exception
     */
    @JvmStatic
    fun isMobileDataEnable(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (EmptyUtils.isEmpty(connectivity)) {
            return false
        }
        val info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (EmptyUtils.isEmpty(info)) {
            return false
        }
        return info!!.isConnected
    }


    /**
     * 判断wifi 是否可用
     *
     * @return
     * @throws Exception
     */
    @JvmStatic
    fun isWifiDataEnable(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (EmptyUtils.isEmpty(connectivity)) {
            return false
        }
        val info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (EmptyUtils.isEmpty(info)) {
            return false
        }
        return info!!.isConnected
    }

    @JvmStatic
    fun getNetworkType(context: Context): Int {
        if (isWifiDataEnable(context)) {
            return NET_WORK_WIFI
        }

        if (isMobileDataEnable(context)) {
            return NET_WORK_WLAN
        }

        return NET_WORK_NONE
    }

    @JvmStatic
    private fun getMacAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val element = interfaces.nextElement()
                if ("wlan0" == element.name) {
                    val macBytes = element.hardwareAddress
                    return ByteUtils.bytes2HexString(macBytes, false, ":")
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}