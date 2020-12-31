package com.example.hwandroidapplication2part2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        findViewById<TextView>(R.id.result).text = intent.getStringExtra(MainActivity.BUNDLE_STRING_KEY)
        findViewById<TextView>(R.id.result_number).text = intent.getIntExtra(MainActivity.BUNDLE_INT_KEY, - 1).toString()
    }
}