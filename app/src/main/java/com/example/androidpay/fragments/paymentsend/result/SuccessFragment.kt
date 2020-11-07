package com.example.androidpay.fragments.paymentsend.result

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidpay.R
import com.example.androidpay.fragments.SharedViewModel
import com.example.androidpay.viewmodels.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_payment_success.view.*


class PaymentSuccessFragment : Fragment() {

    private val mTransactionViewModel:TransactionViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_payment_success, container, false)
        (activity  as AppCompatActivity).setSupportActionBar(view.paymenSuccessToolbar)

//        view.navigateToMain.setOnClickListener { findNavController().navigate(R.id.action_paymentSuccessFragment_to_mainFragment) }
//
//        val newData = Transactiondata(
//            0,args.transactionAmount.toInt(),args.PaymentDetails, LocalDateTime.now().toString(),
//            Date.from(Instant.now()),args.userAccount.id,args.foreignUserAccount.id,args.PaymentMethod,
//            args.transactionType)
//
//
//
//        mTransactionViewModel.insertData(newData)


        return view


    }

    fun date(){

    }


}