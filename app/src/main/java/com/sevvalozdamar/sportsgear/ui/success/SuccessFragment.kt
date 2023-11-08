package com.sevvalozdamar.sportsgear.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentSuccessBinding
import com.sevvalozdamar.sportsgear.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnBackHome.setOnClickListener {
                findNavController().navigate(SuccessFragmentDirections.successToHome())
            }
        }
    }
}