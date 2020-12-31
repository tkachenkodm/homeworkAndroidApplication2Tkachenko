package com.example.hwandroidapplication2part2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setClickListeners()
    }

    companion object {
        const val BUNDLE_STRING_KEY = "STRING_KEY"
        const val BUNDLE_INT_KEY = "INT_KEY"
    }

    private fun setClickListeners() {
        findViewById<Button>(R.id.open_maps).setOnClickListener {
            val coordinates = "geo:50.663, 30.641?z=8"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(coordinates))
            startActivity(intent)
        }

        findViewById<Button>(R.id.open_activity).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(BUNDLE_STRING_KEY, "String for second activity")
                putExtra(BUNDLE_INT_KEY, 45)
            }
            startActivity(intent)
        }
    }

}