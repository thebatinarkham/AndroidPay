package com.example.androidpay.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.androidpay.R

fun hideKeyboard(activity: Activity){
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusView = activity.currentFocus
    currentFocusView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun getProgressDrawable(context:Context):CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
//Extension function
fun ImageView.loadImage(uri:String?,progressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(progressDrawable)
        .error(R.drawable.vector)

    Glide.with(context).setDefaultRequestOptions(options)
        .load(uri).into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView,uri: String?){
    view.loadImage(uri, getProgressDrawable(view.context))
}


