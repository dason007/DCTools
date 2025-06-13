package com.dason.dctools

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.io.BufferedReader
import java.io.InputStreamReader

object AppUtils {

    @JvmStatic
    fun getPackageName(context: Context): String {
        return context.packageName
    }

    @JvmStatic
    fun getPackageManager(context: Context): PackageManager {
        val packageManager = context.packageManager
        return packageManager
    }

    /**
     * 获取单个App的Package信息
     */
    @JvmStatic
    fun getPackageInfo(context: Context, packageName: String?): PackageInfo? {
        val packageManager = getPackageManager(context)
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName!!, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageInfo
    }

    /**
     * 获取单个App的Application信息
     */
    @JvmStatic
    fun getApplicationInfo(context: Context, packageName: String?): ApplicationInfo? {
        val packageManager = getPackageManager(context)
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName!!, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return applicationInfo
    }

    /**
     * 获取单个App图标
     */
    @JvmStatic
    fun getAppIcon(context: Context, packageName: String?): Drawable? {
        val packageManager = getPackageManager(context)
        var icon: Drawable? = null
        try {
            icon = packageManager.getApplicationIcon(packageName!!)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return icon
    }

    /**
     * 获取单个App名称
     */
    @JvmStatic
    fun getAppName(context: Context, packageName: String?): String? {
        val packageManager = getPackageManager(context)
        val realPackageName = packageName ?: getPackageName(context)

        val applicationInfo = getApplicationInfo(context, realPackageName)
        return applicationInfo?.let {
            packageManager.getApplicationLabel(it).toString()
        }
    }

    /**
     * 获取单个App版本号Name
     */
    @JvmStatic
    fun getAppVersionName(context: Context, packageName: String?): String? {
        val packageInfo = getPackageInfo(context, packageName)
        return packageInfo?.versionName
    }

    /**
     * 获取单个App版本号Code
     */
    @JvmStatic
    fun getAppVersionCode(context: Context, packageName: String?): Int {
        val packageInfo = getPackageInfo(context, packageName)
        return packageInfo?.versionCode ?: -1
    }

    /**
     * 应用静默安装
     */
    @JvmStatic
    fun silentInstall(apkPath: String?): Array<String> {
        var process: Process? = null
        var errorResult: BufferedReader? = null
        var successResult: BufferedReader? = null
        val errorMsg = StringBuilder()
        val successMsg = StringBuilder()
        try {
            process = ProcessBuilder("pm", "install", "-r", apkPath).start()
            errorResult = BufferedReader(InputStreamReader(process.errorStream))
            successResult = BufferedReader(InputStreamReader(process.inputStream))
            var s: String?
            while ((errorResult.readLine().also { s = it }) != null) {
                errorMsg.append(s)
            }
            while ((successResult.readLine().also { s = it }) != null) {
                successMsg.append(s)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                successResult?.close()
            } catch (_: Exception) {
            }
            try {
                errorResult?.close()
            } catch (_: Exception) {
            }
            process?.destroy()
        }
        return arrayOf(
            errorMsg.toString(),
            successMsg.toString()
        )
    }

}