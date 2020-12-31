package com.example.hwandroidapplication2part3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectionStateChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = Toast.makeText(context, "Network state changed", Toast.LENGTH_SHORT)
        message.show()
    }
}