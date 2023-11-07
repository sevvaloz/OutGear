package com.sevvalozdamar.sportsgear.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentPaymentBinding
import com.sevvalozdamar.sportsgear.ui.cart.CartViewModel
import com.sevvalozdamar.sportsgear.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val args by navArgs<PaymentFragmentArgs>()
    private val viewModel by viewModels<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivBack.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }

            tvTotal.text = args.total

            val cardHolder = etNameSurname.text
            val cardNumber = etCardNumber.text
            val cardMonth = etCreditCartMonth.text
            val cardYear = etCreditCartYear.text
            val cvv = etCreditCartCvv.text
            val address = etAddress.text
            val fields = listOf(cardHolder,cardNumber,cardMonth,cardYear,cvv,address)

            btnPlaceOrder.setOnClickListener {
                if (fields.all {!it.isNullOrEmpty()}) {
                    viewModel.clearCart()
                    findNavController().navigate(PaymentFragmentDirections.paymentToSuccess())
                } else {
                    Snackbar.make(requireView(), "Fill the blanks", 2000)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                        .show()
                }
            }
        }
    }

}