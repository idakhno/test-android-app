package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        val inputText = findViewById<EditText>(R.id.inputText)
        val openSecondActivityButton = findViewById<Button>(R.id.openSecondActivityButton)

        openSecondActivityButton.setOnClickListener {
            val message = inputText.text.toString()

            if (message.isBlank()) {
                inputText.error = getString(R.string.error_empty_text)
                return@setOnClickListener
            }

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("message", message)
            startActivity(intent)
        }

        val dialButton = findViewById<Button>(R.id.dialButton)

        dialButton.setOnClickListener {
            val phone = inputText.text.toString().trim()

            if (!phone.matches(Regex("\\+?[0-9]{7,15}"))) {
                inputText.error = getString(R.string.error_invalid_phone)
                return@setOnClickListener
            }

            val dialIntent = Intent(
                Intent.ACTION_DIAL,
                Uri.fromParts("tel", phone, null)
            )

            try {
                startActivity(dialIntent)
            } catch (_: ActivityNotFoundException) {
                inputText.error = getString(R.string.error_no_dialer)
            }
        }

        val shareButton = findViewById<Button>(R.id.shareButton)

        shareButton.setOnClickListener {
            val message = inputText.text.toString()

            if (message.isBlank()) {
                inputText.error = getString(R.string.error_empty_text)
                return@setOnClickListener
            }

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)

            val chooser = Intent.createChooser(
                shareIntent,
                getString(R.string.share_text)
            )
            startActivity(chooser)
        }
    }
}