package com.example.androidpay.fragments.main.adapter.user

import androidx.recyclerview.widget.DiffUtil
import com.example.androidpay.data.models.UserData


class UserDiffUtil(private val oldList: List<UserData>,
                   private val newList:List<UserData>
): DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id === newList[newItemPosition].id
                &&  oldList[oldItemPosition].title === newList[newItemPosition].title
                &&  oldList[oldItemPosition].image_url === newList[newItemPosition].image_url
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }



}