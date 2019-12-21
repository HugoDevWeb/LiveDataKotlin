package com.example.td2

import SHARED_PREF_TOKEN_KEY
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.td2.network.Api
import com.example.td2.network.UserService

class AuthenticationActivity<fragment> : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication)

    }

}