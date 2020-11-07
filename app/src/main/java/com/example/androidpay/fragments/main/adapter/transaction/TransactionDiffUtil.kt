package com.example.androidpay.fragments.main.adapter.transaction

import androidx.recyclerview.widget.DiffUtil
import com.example.androidpay.data.models.TransactionWithUserData


class TransactionDiffUtil(private val oldList:List<TransactionWithUserData>,
                          private val newList:List<TransactionWithUserData>)
    :DiffUtil.Callback()
{
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].transactionId === newList[newItemPosition].transactionId
                &&  oldList[oldItemPosition].userId === newList[newItemPosition].userId
                &&  oldList[oldItemPosition].transactionAmount === newList[newItemPosition].transactionAmount
                &&  oldList[oldItemPosition].transactionDescription === newList[newItemPosition].transactionDescription
                &&  oldList[oldItemPosition].transactionTime === newList[newItemPosition].transactionTime
                &&  oldList[oldItemPosition].transactionType === newList[newItemPosition].transactionType

                &&  oldList[oldItemPosition].foreignAccountId === newList[newItemPosition].foreignAccountId
                &&  oldList[oldItemPosition].foreignUserTitle === newList[newItemPosition].foreignUserTitle
                &&  oldList[oldItemPosition].foreignUserImageUrl === newList[newItemPosition].foreignUserImageUrl

    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }



}