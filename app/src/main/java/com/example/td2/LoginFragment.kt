package com.example.td2


import SHARED_PREF_TOKEN_KEY
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.provider.Settings.Secure.putString
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.td2.network.Api
import com.example.td2.network.UserService
import kotlinx.android.synthetic.main.fragment_authentication.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    val userService = Api.INSTANCE.userService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_submit.setOnClickListener{
            login()
        }
    }

    private fun login() {

        val email = login_email.text.toString()
        val password = login_password.text.toString()
        val user = LoginForm(email = email, password = password)
        val intent = Intent(context, MainActivity::class.java)
        if (email != "" && password != "") {
            lifecycleScope.launch {
                val user = userService.login(user)
                PreferenceManager.getDefaultSharedPreferences(context).edit {
                    putString(SHARED_PREF_TOKEN_KEY, user.body()?.token.toString())
                }
            }
            startActivity(intent)

        }
        else {
            Toast.makeText(context, "Impossible de se connecter", Toast.LENGTH_LONG).show()

        }


    }
}
