package com.sevvalozdamar.sportsgear.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.utils.Firebase
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.databinding.FragmentSigninBinding
import com.sevvalozdamar.sportsgear.ui.signup.SignupFragmentDirections
import com.sevvalozdamar.sportsgear.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment(R.layout.fragment_signin) {

    private val binding by viewBinding(FragmentSigninBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.currentUser.let {
            findNavController().navigate(SigninFragmentDirections.signinToHome())
        }

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (Utility.checkFields(email, password)) {
                    signIn(email, password)
                }
            }
            txtToSignup.setOnClickListener {
                findNavController().navigate(SigninFragmentDirections.signinToSignup())
            }
        }
    }

    private fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(SigninFragmentDirections.signinToHome())
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }

}