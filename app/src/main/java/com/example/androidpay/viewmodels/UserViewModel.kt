package com.example.androidpay.viewmodels

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpay.data.TransactionDatabase
import com.example.androidpay.data.api.BaseApiService
import com.example.androidpay.data.models.UserData
import com.example.androidpay.repository.UserRepository
import com.example.androidpay.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionDao = TransactionDatabase.getDatabase(application).userDao()
    private val repository: UserRepository

    val getAllData: LiveData<List<UserData>>
    private val baseApiService = BaseApiService()
    private val disposable = CompositeDisposable()

    private var prefHelper = SharedPreferencesHelper(getApplication())

    init {
        if (!prefHelper.isUserSaveToDatabase()!!) fetchUserFromRemote()
        if(!prefHelper.isCurrentUserSave()!!) createMockCurrentUser()
        repository = UserRepository(transactionDao)
        getAllData = repository.getAllData
    }

    fun getCurrentUser(): UserData = prefHelper.getCurrentUser()

    fun saveCurrentUser(currentUser:UserData) = prefHelper.saveCurrentUser(currentUser)

    fun insertData(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(userData)
        }
    }

    fun updateData(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(userData)
        }
    }


    fun deleteItem(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(userData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createMockCurrentUser() {
        val currentUser = UserData(
            0,
            "User Name",
            "https://raw.githubusercontent.com/thebatinarkham/android-pay-Api/main/images/046-mummy%20min.png",
            "user@gmail.com",
            "+91 924 348 1393"
        )
        prefHelper.saveCurrentUser(currentUser)
    }

    private fun fetchUserFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            disposable.add(
                baseApiService.getUser().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<UserData>>() {
                        override fun onSuccess(userData: List<UserData>) {
                            userData.forEach { insertData(it) }
                            prefHelper.saveUserDataToDatabase(true)
                            Toast.makeText(
                                getApplication(),
                                "Userdata Retrieved From End Point.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                getApplication(),
                                "Something wrong happen: ${e.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    ))
        }
    }


}