package com.example.androidpay.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpay.data.TransactionDatabase
import com.example.androidpay.data.api.BaseApiService
import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.TransactionWithUserData
import com.example.androidpay.repository.TransactionRepository
import com.example.androidpay.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application):AndroidViewModel(application) {
    private val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()

    private val repository :TransactionRepository
    private val getAllData:LiveData<List<TransactionData>>
    val getTransactionWithUserData:LiveData<List<TransactionWithUserData>>
    private val baseApiService = BaseApiService()


    private var prefHelper = SharedPreferencesHelper(getApplication())
    init{
        if(!prefHelper.isTransactionSaveToDatabase()!! && !prefHelper.isUserSaveToDatabase()!!)fetchTransactionFromRemote()
        repository = TransactionRepository(transactionDao)
        getAllData = repository.getAllData
        getTransactionWithUserData = repository.getTransactionHistoryWithUserHistory
    }

    fun insertData(transactionData: TransactionData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(transactionData)
        }
    }

    fun updateData(transactionData: TransactionData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(transactionData)
        }
    }

    fun deleteItem(transactionData: TransactionData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(transactionData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun searchTransactionById(searchId :Int):LiveData<TransactionData>{
        return repository.searchTransactionById(searchId)
    }

    private fun fetchTransactionFromRemote(){
    viewModelScope.launch(Dispatchers.IO){
        baseApiService.getTransaction().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object  : DisposableSingleObserver<List<TransactionData>>(){
                override fun onSuccess(transactionData: List<TransactionData>) {
                    transactionData.forEach{insertData(it)}
                    prefHelper.saveTransactionToDatabase(true)
                    Toast.makeText(getApplication(),"Transactiondata Retrieved From End Point.",Toast.LENGTH_LONG).show()
                }
                override fun onError(e: Throwable) {
                    Toast.makeText(getApplication(),"Something wrong happen: ${e.localizedMessage}",
                        Toast.LENGTH_LONG).show()
                }
            })
    }
    }


}