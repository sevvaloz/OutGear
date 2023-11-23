package com.sevvalozdamar.sportsgear.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentProfileBinding
import com.sevvalozdamar.sportsgear.ui.main.MainActivity
import com.sevvalozdamar.sportsgear.ui.signin.SigninFragmentDirections
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivLogout.setOnClickListener {
                signOut()
            }
        }
        observe()
        viewModel.getUserInfo()
    }

    private fun observe(){
        viewModel.user.observe(viewLifecycleOwner){ state ->
            with(binding){
                when (state) {
                    Resource.Loading -> {
                        binding.progressBar.visible()
                    }

                    is Resource.Success -> {
                        binding.progressBar.gone()
                        nameSurname.visible()
                        email.visible()
                        ivLogout.visible()
                        nameSurname.text = "${state.data.name} ${state.data.surname}"
                        email.text = state.data.email
                    }

                    is Resource.Fail -> {
                        binding.progressBar.gone()
                        nameSurname.gone()
                        email.gone()
                        ivLogout.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun signOut() {
        requireActivity().finish()
        viewModel.signOut()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}