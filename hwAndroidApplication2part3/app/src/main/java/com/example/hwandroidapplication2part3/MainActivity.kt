package com.example.hwandroidapplication2part3

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(ConnectionStateChangeReceiver(), IntentFilter(STATE_ACTION))
        registerReceiver(ClickReceiver(), IntentFilter(CLICK_ACTION))

        findViewById<Button>(R.id.create_log).setOnClickListener {
            val intent = Intent(CLICK_ACTION)
            sendBroadcast(intent)
        }
    }

    companion object {
        const val STATE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
        const val CLICK_ACTION = "ACTION_CLICK"
    }
}