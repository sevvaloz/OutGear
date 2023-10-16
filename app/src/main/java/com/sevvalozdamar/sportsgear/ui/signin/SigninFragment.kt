package com.sevvalozdamar.sportsgear.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.util.Firebase
import com.sevvalozdamar.sportsgear.common.viewBinding
import com.sevvalozdamar.sportsgear.databinding.FragmentSigninBinding
import com.sevvalozdamar.sportsgear.util.Utility

class SigninFragment : Fragment(R.layout.fragment_signin) {

    private val binding by viewBinding(FragmentSigninBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (Utility.checkFields(email, password)) {
                    signIn(email, password)
                }
            }
            txtToSignup.setOnClickListener {
                findNavController().navigate(R.id.signin_to_signup)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.signin_to_home)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }


}