package com.example.androidpay.fragments

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.androidpay.data.models.TransactionWithUserData
import com.example.androidpay.fragments.main.MainFragmentDirections
import com.example.androidpay.utils.getProgressDrawable
import com.example.androidpay.utils.loadImage
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

//custom adapter
class BindingAdapter {

    companion object {
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @BindingAdapter("android:transactionDuration")
        @JvmStatic
        fun calculateTransactionDuration(view: TextView,transactionTime:String){
            var simpleDateFormat =  SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
//        Sun Oct 25 16:28:26 GMT+05:30 2020

            var transactionTime: Date = simpleDateFormat.parse(transactionTime)
            var currentTime : Date = simpleDateFormat.parse(simpleDateFormat.format( Date.from(
                Instant.now())))

            var diff_in_time  = currentTime.time - transactionTime.time

            var diff_in_second = (diff_in_time / 1000 % 6)

            var diff_in_minutes = (diff_in_time / (1000 * 60) % 60)

            var diff_in_hour = (diff_in_time / (1000 * 60 * 60 ) % 24)

            var diff_in_days = (diff_in_time / (10001 * 60 * 60 *24 ) % 365)


            return   if(diff_in_hour in 1..23){
                view.text = " $diff_in_hour hour ago"
            }else if(diff_in_hour < 1){
                view.text = "$diff_in_minutes minute ago"
            }else {
                view.text = "$diff_in_days day ago"
            }

        }


        @BindingAdapter("android:navigateToDetails")
        @JvmStatic
        fun navigateToDetailsFragment(view:ConstraintLayout,args: TransactionWithUserData){
            view.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(args)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:setDetails")
        @JvmStatic
        fun setDetailText(view:TextView,args:TransactionWithUserData){
               view.text =  "Payment " + args.transactionType +" To " +
                       args.foreignUserTitle +  " for " + args.transactionDescription + " via " +
                       args.paymentMethod
        }


    }




}