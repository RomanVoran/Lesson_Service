package com.example.lockscreen

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TEST_TAG", "Service onStartCommand started id = $startId")
        onTickTockStart()
        return super.onStartCommand(intent, flags, startId)
//        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.w("TEST_TAG", "Service processName = ${App.getThreadName()}")


    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("TEST_TAG", "Service onDestroy")
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("TEST_TAG", "Service stop")
        return super.stopService(name)
    }


    private fun onTickTockStart() {
        GlobalScope.launch(Dispatchers.IO) {
            for (i in (0..4)) {
                delay(2000)
                Log.d("TEST_TAG", "TICK! $i")
            }
            stopSelf()
        }
    }
}