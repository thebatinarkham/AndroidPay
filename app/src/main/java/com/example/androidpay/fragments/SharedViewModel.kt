package com.example.androidpay.fragments

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.androidpay.R
import com.example.androidpay.data.models.TransactionType
import com.example.androidpay.data.models.UserData

class SharedViewModel(application:Application):AndroidViewModel(application) {
    private val emptyDatabase :MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfUserDatabaseEmpty(userData:List<UserData>){
        emptyDatabase.value = userData.isEmpty()
    }


    fun verifyDataFromUser(title:String,description:String):Boolean{
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parseTransactionType(value: String):TransactionType{
        return when(value){
            "SEND" -> TransactionType.SEND
            "REQUEST" -> TransactionType.REQUEST
            else -> TransactionType.SEND
        }
    }

    fun inflateImageToMenu(imageUrl:String, menu: Menu){
        Glide.with(getApplication<Application>().applicationContext)
            .asBitmap()
            .load(imageUrl)
            .error(R.drawable.vector)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    menu.findItem(R.id.user_avtar).icon = placeholder
                }
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                ) {
                    menu.findItem(R.id.user_avtar).icon = BitmapDrawable(
                        getApplication<Application>().resources, Bitmap.createScaledBitmap(resource, 65, 65, true)
                    )

                }
            })}

    var mCardTextWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            if(s?.length!! > 0 && s?.length % 5 === 0){
                val c:Char = s?.get(s?.length -1)
                if('-' == c){
                    s?.delete(s?.length -1,s?.length)
                }
            }
            if(s?.length!! > 0 && s?.length % 5 === 0){
                val c:Char = s?.get(s?.length -1)
                if (Character.isDigit(c) && TextUtils.split(
                        s?.toString(),
                        "-"
                    ).size <= 5
                ) {
                    s.insert(s?.length - 1, "-")
                }
            }
        }
    }
    var mCardExpireDateTextWatcher = object:TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.length!! > 0 && s?.length % 3 === 0) {
                val c: Char = s?.get(s?.length - 1)
                if ('/' == c) {
                    s?.delete(s?.length - 1, s?.length)
                }
            }
            if (s?.length > 0 && s?.length % 3 === 0) {
                val c: Char = s?.get(s?.length - 1)
                if (Character.isDigit(c) && TextUtils.split(
                        s?.toString(),
                        "/"
                    ).size <= 2
                ) {
                    s.insert(s?.length - 1, "/")
                }
            }
        }

    }
}