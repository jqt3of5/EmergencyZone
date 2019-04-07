package com.substantive.prepare.login

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.substantive.prepare.R
import android.text.Spanned
import android.widget.ImageView


class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        var close = findViewById<ImageView>(R.id.cancel_login)
        close.setOnClickListener {
            finish()
        }

        var username = findViewById<EditText>(R.id.username_edittext).text
        var password = findViewById<EditText>(R.id.password_edittext).text

        var button = findViewById<Button>(R.id.login_button)
        button.setOnClickListener {

        }

        var forgotPasswordButton = findViewById<TextView>(R.id.forgot_password_button)
        forgotPasswordButton.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.emergencyzone.com/login_sendpass.asp"))
            startActivity(intent)
        }

        var dontHaveAccount = findViewById<TextView>(R.id.dont_have_account_button)
        dontHaveAccount.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.emergencyzone.com/AccountSettings.asp?AddNewCustomer=Y&ReturnTo="))
            startActivity(intent)
        }
    }

    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }
}