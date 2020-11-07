package com.example.androidpay.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.androidpay.data.models.UserData
import com.google.gson.Gson


class SharedPreferencesHelper {
    companion object{
        private var pref: SharedPreferences? = null
        private const val  IS_USER_SAVE = "is userSave"
        private const val IS_TRANSACTION_SAVE = "is transactionSave"
        private val CURRENT_USER: String? = "current User"
        private const val IS_CURRENT_USER_SAVE = "is currentUserSave"

        @Volatile private var instance: SharedPreferencesHelper ? = null

        private val LOCK = Any()

        operator  fun invoke(context: Context):SharedPreferencesHelper = instance ?:
                synchronized(LOCK){
                    instance ?: buildHelper(context).also{
                        instance = it
                    }
                }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            pref = PreferenceManager.getDefaultSharedPreferences(context)

            return SharedPreferencesHelper()
        }
    }

    fun saveUserDataToDatabase(isSave :Boolean){
        pref?.edit(commit = true){
            putBoolean(IS_USER_SAVE,isSave)
        }
    }

    fun isUserSaveToDatabase() = pref?.getBoolean(IS_USER_SAVE,false)

    fun saveTransactionToDatabase(isSave: Boolean){
        pref?.edit(commit = true){
            putBoolean(IS_TRANSACTION_SAVE,isSave)
        }
    }

    fun isTransactionSaveToDatabase() = pref?.getBoolean(IS_TRANSACTION_SAVE,false)

    @RequiresApi(Build.VERSION_CODES.P)
    fun saveCurrentUser(user: UserData){
        pref?.edit(commit = true){
            putString(CURRENT_USER, Gson().toJson(user))
            putBoolean(IS_CURRENT_USER_SAVE,true)
        }
    }

    fun isCurrentUserSave()= pref?.getBoolean(IS_CURRENT_USER_SAVE,false)

    fun getCurrentUser():UserData = Gson().fromJson(pref?.getString(CURRENT_USER,null),UserData::class.java)


}