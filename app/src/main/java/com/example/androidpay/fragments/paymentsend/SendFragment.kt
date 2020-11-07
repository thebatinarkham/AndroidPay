package com.example.androidpay.fragments.paymentsend

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidpay.R
import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.databinding.FragmentSendBinding
import com.example.androidpay.fragments.SharedViewModel
import com.example.androidpay.utils.hideKeyboard
import com.example.androidpay.viewmodels.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_send.view.*
import java.time.Instant
import java.util.*


class SendFragment : Fragment() {
    private val args by navArgs<SendFragmentArgs>()
    private val mTransactionViewModel: TransactionViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSendBinding.inflate(inflater,container,false)
        binding.args = args


        (activity as AppCompatActivity).setSupportActionBar(binding.root.detailsToolbar)

        binding.root.expireDateTextView.addTextChangedListener(mSharedViewModel.mCardExpireDateTextWatcher)
        binding.root.accountNumberTextView.addTextChangedListener(mSharedViewModel.mCardTextWatcher)

        binding.root.navigateTransactionResult.setOnClickListener {
            navigateToMain(binding.root)
        }

        //Set menu
        setHasOptionsMenu(true)

        //hide soft keyboard
        hideKeyboard(requireActivity())

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigateToMain(view: View) {
        if(bankNameTextView.text.toString().trim().isNotEmpty() && accountNumberTextView.text.toString().trim().isNotEmpty()
            && paymentDetailsText.text.toString().trim().isNotEmpty()){
            val newData = TransactionData(
                0,
                args.transactionAmount.toLong(),
                paymentDetailsText.text.toString(),
                Date.from(Instant.now()).toString(),
                args.userAccount.id,
                args.foreignUserAccount.id,
                "Card",
                args.transactionType,
                view.bankNameTextView.text.toString(),
                view.accountNumberTextView.text.toString(),
                view.expireDateTextView.text.toString(),
                view.accountNumberTextView.text.toString(),
                ""
            )
            mTransactionViewModel.insertData(newData)
            findNavController().navigate(R.id.action_paymentDetailsFragment_to_mainFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields.",Toast.LENGTH_SHORT).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_avtar_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        mSharedViewModel.inflateImageToMenu(args.foreignUserAccount.image_url,menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


