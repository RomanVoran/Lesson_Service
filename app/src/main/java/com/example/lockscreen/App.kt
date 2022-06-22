package com.example.lockscreen

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock


class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Log.w("TEST_TAG", "Application processName = ${getThreadName()}")
    }


    companion object {
        fun getProcessName(): String? {
            return if (Build.VERSION.SDK_INT >= 28) Application.getProcessName() else try {
                @SuppressLint("PrivateApi") val activityThread =
                    Class.forName("android.app.ActivityThread")

                // Before API 18, the method was incorrectly named "currentPackageName", but it still returned the process name
                // See https://github.com/aosp-mirror/platform_frameworks_base/commit/b57a50bd16ce25db441da5c1b63d48721bb90687
                val methodName =
                    if (Build.VERSION.SDK_INT >= 18) "currentProcessName" else "currentPackageName"
                val getProcessName = activityThread.getDeclaredMethod(methodName)
                getProcessName.invoke(null) as String
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            }

            // Using the same technique as Application.getProcessName() for older devices
            // Using reflection since ActivityThread is an internal API
        }


        fun getThreadName() = Thread.currentThread().name

    }
}