package com.example.todo

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService


class Alertreceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context,"DO WORK",Toast.LENGTH_LONG).show()

    }
}