package com.vustuntas.nutrimeter.viewmodel
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.vustuntas.nutrimeter.model.Nutrition
import com.vustuntas.nutrimeter.service.NutriAPIService
import com.vustuntas.nutrimeter.service.NutriDatabase
import com.vustuntas.nutrimeter.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NutririonListViewModel(application: Application): AndroidViewModel(application ){
    val nutritions = MutableLiveData<List<Nutrition>>()
    val nutritionsErrorMessage = MutableLiveData<Boolean>()
    val nutriLoading = MutableLiveData<Boolean>()
    private var nutriApiService = NutriAPIService()
    private var compositeDisposable =  CompositeDisposable()
    private var privateSharedPreferences:PrivateSharedPreferences = PrivateSharedPreferences(getApplication())

    private var updateTime = 0.10 * 60 * 1000 * 1000 * 1000L

    private lateinit var job : Job

    fun refreshData(){
        val savedTime = privateSharedPreferences.getTime()
        if(savedTime != 0L && System.nanoTime() - savedTime < updateTime){
            getDataFromRoom()
        }
        else
            getDataFromInternet()
    }

    private fun getDataFromRoom(){
        job = CoroutineScope(Dispatchers.IO).launch {
            async {
                var listNutri : List<Nutrition>?=null
                val dao = NutriDatabase(getApplication()).nutriDao()
                listNutri = dao.getAllNutrition()
                showNutritions(listNutri!!)
                withContext(Dispatchers.Main){
                    Toast.makeText(getApplication(),"Nutri get Room",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getDataFromInternet(){
        compositeDisposable.add(
            nutriApiService.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Nutrition>>(){
                    override fun onSuccess(t: List<Nutrition>) {
                        uploadRoom(t)
                        Toast.makeText(getApplication(),"Nutri get Internet",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        nutriLoading.value = false
                        nutritionsErrorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showNutritions(nutriList : List<Nutrition>){
        nutritions.postValue(nutriList)
        nutriLoading.postValue(false)
        nutritionsErrorMessage.postValue(false)
    }

    private fun uploadRoom(nutriList : List<Nutrition>){

        job = CoroutineScope(Dispatchers.IO).launch {
            val dao = NutriDatabase(getApplication()).nutriDao()
            dao.deleteAllNutri()

            //val idList = dao.insertAll(*nutriList.toTypedArray())
            val idList = arrayListOf<Long>()
            nutriList.forEach {
                idList.add(dao.insertAll(it))
            }


            var i = 0
            while(i<idList.size){
                nutriList.get(i).ID = idList.get(i).toInt()
                i++

            }
            showNutritions(nutriList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}