package com.example.androidpay.fragments.paymentrequest

import android.os.Build
import android.os.Bundle
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
import com.example.androidpay.databinding.FragmentRequestBinding
import com.example.androidpay.fragments.SharedViewModel
import com.example.androidpay.utils.hideKeyboard
import com.example.androidpay.viewmodels.TransactionViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_request.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import java.time.Instant
import java.util.*


class RequestFragment : Fragment() {
    private val args by navArgs<RequestFragmentArgs>()
    private val mTransactionViewModel: TransactionViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()
    private var _binding:FragmentRequestBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestBinding.inflate(inflater,container,false)
        binding.args = args

        (activity as AppCompatActivity).setSupportActionBar(binding.root.requestToolbar)

        val builder = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("SELECT A DATE")
        val materialDatePicker = builder.build()
        binding.root.requestDateTextView.setOnClickListener {
            if(!materialDatePicker.isVisible){
                materialDatePicker.show(
                    (activity as AppCompatActivity).supportFragmentManager,
                    "DATE_PICKER")
            }
        }
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.root.requestDateTextView.setText(materialDatePicker.headerText)
        }

        binding.root.accountNumberTextView.addTextChangedListener(mSharedViewModel.mCardTextWatcher)

        binding.root.navigateRequest.setOnClickListener { navigateToMain()}

        //Set menu
        setHasOptionsMenu(true)

        //hide soft keyboard
        hideKeyboard(requireActivity())

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigateToMain() {
            if(accountNumberTextView.text.toString().trim().isNotEmpty() &&
                requestDateTextView.text.toString().trim().isNotEmpty()
                && requestDetailsText.text.toString().trim().isNotEmpty()){
                val newData = TransactionData(0,
                    args.transactionAmount.toLong(),
                    requestDetailsText.text.toString(),
                    Date.from(Instant.now()).toString(),
                    args.userAccount.id,
                    args.foreignUserAccount.id,
                    "",
                    args.transactionType,
                    "",
                    accountNumberTextView.text.toString(),
                    "",
                    "",
                    requestDateTextView.text.toString()
                )
                mTransactionViewModel.insertData(newData)
                findNavController().navigate(R.id.action_requestFragment_to_mainFragment)
            }else{
                Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show()
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