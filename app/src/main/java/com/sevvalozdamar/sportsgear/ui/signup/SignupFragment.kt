package com.sevvalozdamar.sportsgear.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.data.model.User
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.databinding.FragmentSignupBinding
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val viewModel: SignupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (checkFields(email, password)) {
                    viewModel.signUpWithEmailAndPassword(User(email), password)
                }
            }
            txtToSignin.setOnClickListener {
                findNavController().navigate(SignupFragmentDirections.signupToSignin())
            }
        }

    }

    private fun observe() {
        with(binding) {
            with(viewModel) {

                checkCurrentUser.observe(viewLifecycleOwner) {
                    if (it) {
                        findNavController().navigate(SignupFragmentDirections.signupToHome())
                    }
                }
                result.observe(viewLifecycleOwner) {
                    when (it) {
                        Resource.Loading -> {
                            progressBar.visible()
                            cl.gone()
                        }

                        is Resource.Success -> {
                            progressBar.gone()
                            cl.visible()
                            findNavController().navigate(SignupFragmentDirections.signupToHome())
                        }

                        is Resource.Fail -> {
                            progressBar.gone()
                            cl.visible()
                            Snackbar.make(requireView(), it.failMessage, 2000)
                                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                                .show()
                        }

                        is Resource.Error -> {
                            progressBar.gone()
                            cl.visible()
                            Snackbar.make(requireView(), it.errorMessage, 2000)
                                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        binding.apply {
            if (email.isEmpty()) {
                Snackbar.make(requireView(), "Fill in the blanks", 2000)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                    .show()
                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(requireView(), "Invalid e-mail format", 2000)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                    .show()
                return false
            }

            if (password.length < 6) {
                Snackbar.make(requireView(), "Password must be minimum 6 characters", 2000)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                    .show()
                return false
            }
        }
        return true
    }

}