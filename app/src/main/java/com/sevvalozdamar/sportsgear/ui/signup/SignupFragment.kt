package com.sevvalozdamar.sportsgear.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.util.Firebase
import com.sevvalozdamar.sportsgear.common.viewBinding
import com.sevvalozdamar.sportsgear.databinding.FragmentSignupBinding
import com.sevvalozdamar.sportsgear.util.Utility

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (Utility.checkFields(email, password)) {
                    signUp(email, password)
                }
            }
            txtToSignin.setOnClickListener {
                findNavController().navigate(R.id.signup_to_signin)
            }
        }
    }

    private fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Snackbar.make(requireView(),"Account created successfully", 1000).show()
            findNavController().navigate(R.id.signup_to_home)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }

}