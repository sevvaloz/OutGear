package com.sevvalozdamar.sportsgear.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.databinding.FragmentSigninBinding
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninFragment : Fragment(R.layout.fragment_signin) {

    private val binding by viewBinding(FragmentSigninBinding::bind)
    private val viewModel: SigninViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkFields(email, password)) {
                    viewModel.signinWithEmailAndPassword(email,password)
                }
            }
            txtToSignup.setOnClickListener {
                findNavController().navigate(SigninFragmentDirections.signinToSignup())
            }
        }
    }

    private fun observe() {
        with(binding) {

            viewModel.checkCurrentUser.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(SigninFragmentDirections.signinToHome())
                }
            }

            viewModel.result.observe(viewLifecycleOwner) {
                when (it) {
                    Resource.Loading -> {
                        binding.progressBar.visible()
                        cl.gone()
                    }

                    is Resource.Success -> {
                        binding.progressBar.gone()
                        cl.visible()
                        findNavController().navigate(SigninFragmentDirections.signinToHome())
                    }

                    is Resource.Fail -> {
                        binding.progressBar.gone()
                        cl.visible()
                        Snackbar.make(requireView(), it.failMessage, 2000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        cl.visible()
                        Snackbar.make(requireView(), it.errorMessage, 2000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                }
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        binding.apply {
            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(requireView(), "Fill the blanks", 2000)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                    .show()
                return false
            }
        }
        return true
    }

}