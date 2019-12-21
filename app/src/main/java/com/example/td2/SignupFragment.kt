package com.example.td2


import SHARED_PREF_TOKEN_KEY
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.td2.network.Api
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : Fragment() {

    val userService = Api.INSTANCE.userService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_submit_signup.setOnClickListener{
            signup()
        }
    }


    private fun signup() {
        val firstname = signup_firstname.text.toString()
        val lastname = signup_lastname.text.toString()
        val email = signup_email.text.toString()
        val password = signup_password.text.toString()
        val confirmPassword = signup_confirm_password.text.toString()


        val user = SignupForm(firstname = firstname, lastname = lastname, email = email, password = password, confirmPassword = confirmPassword)

        if (firstname != "" && lastname != "" && email != "" && password != "" && confirmPassword != "" ){
            if (confirmPassword == password) {
                lifecycleScope.launch {
                    userService.signUp(user)
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(SHARED_PREF_TOKEN_KEY, Api.TOKEN).apply()
                }
            } else {
                Toast.makeText(context, "Impossible de créer ce compte, veuillez vérifier les informations", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Impossible de créer ce compte, veuillez vérifier les informations", Toast.LENGTH_LONG).show()
        }

    }

}
