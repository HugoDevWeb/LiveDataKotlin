package com.example.td2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_authentication.*

/**
 * A simple [Fragment] subclass.
 */
class AuthenticationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }
        signUp.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}
