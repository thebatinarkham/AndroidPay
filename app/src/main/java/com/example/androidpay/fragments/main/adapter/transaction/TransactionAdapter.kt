package com.example.androidpay.fragments.main.adapter.transaction

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpay.data.models.TransactionWithUserData
import com.example.androidpay.databinding.TransactionLayoutBinding

class TransactionAdapter :RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    private var dataList = emptyList<TransactionWithUserData>()

    class ViewHolder (private val binding: TransactionLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(transactionWithUserData: TransactionWithUserData){
            binding.args = transactionWithUserData
            binding.executePendingBindings()//update view in transaction layout
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionLayoutBinding.inflate(layoutInflater,parent,false)
                return  ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = dataList[position]
        holder.bind(currentItem)
    }


    override fun getItemCount(): Int {
        return  dataList.size
    }

    fun setData(transactionWithUserData: List<TransactionWithUserData>){
        val transactionDiffUtil = TransactionDiffUtil(dataList,transactionWithUserData)
        val transactionDiffUtilResult = DiffUtil.calculateDiff(transactionDiffUtil)
        this.dataList = transactionWithUserData
        transactionDiffUtilResult.dispatchUpdatesTo(this)
    }




}