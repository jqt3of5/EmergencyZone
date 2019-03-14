package com.emergencyzone.emergencyzone.login

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.emergencyzone.emergencyzone.R
import android.text.Spanned
import android.text.method.LinkMovementMethod


class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        var button = findViewById<Button>(R.id.login_button)
        var username = findViewById<EditText>(R.id.username_edittext)
        var password = findViewById<EditText>(R.id.password_edittext)

        var forgotPasswordLink = findViewById<TextView>(R.id.forgot_password_link)
        var source = "<a href='example.com'>Forgot Password</a>"
        forgotPasswordLink.text = fromHtml(source)
        forgotPasswordLink.movementMethod = LinkMovementMethod.getInstance()
    }

    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }
}