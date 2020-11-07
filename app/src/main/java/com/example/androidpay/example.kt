package com.example.androidpay

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    println(LocalDateTime.now().toString())
    println(Date.from(Instant.now()))
}