package com.example.hwandroidapplication2part3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("CLICK_RECEIVER", "user clicked button")
    }
}