package com.emergencyzone.emergencyzone

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        var button = findViewById<Button>(R.id.login_button)
        var username = findViewById<EditText>(R.id.username_edittext)
        var password = findViewById<EditText>(R.id.password_edittext)

    }

}