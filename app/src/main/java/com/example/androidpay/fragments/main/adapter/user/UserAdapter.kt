package com.example.androidpay.fragments.main.adapter.user

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpay.data.models.UserData
import com.example.androidpay.databinding.UserLayoutBinding

class UserAdapter(private val context: Context,private val listener: ItemClickListener)
    :RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    var dataList = emptyList<UserData>()
    var selectedItem = -1


    class ViewHolder (private val binding:UserLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData){
            binding.args = userData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserLayoutBinding.inflate(layoutInflater,parent,false)
                return  ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem  = dataList[position]
        holder.bind(currentItem)
//        holder.itemView.user_name.text = dataList[position].title.substringBefore(" ")
        holder.itemView.setOnClickListener {
            listener.itemViewListClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(userData: List<UserData>){
        val userDiffUtil = UserDiffUtil(dataList,userData)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        this.dataList = userData
        userDiffUtilResult.dispatchUpdatesTo(this)

    }

}