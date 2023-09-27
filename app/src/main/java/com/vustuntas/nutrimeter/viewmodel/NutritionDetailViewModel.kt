package com.vustuntas.nutrimeter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vustuntas.nutrimeter.model.Nutrition
import com.vustuntas.nutrimeter.service.NutriDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NutritionDetailViewModel(application: Application) :AndroidViewModel(application) {
    private lateinit var job : Job
    val nutritions =  MutableLiveData<Nutrition>()

    fun getRoomData(uuid : Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            async {
                val dao = NutriDatabase(getApplication()).nutriDao()
                val nutri = dao.getNutri(uuid)
                nutritions.postValue(nutri)
            }.await()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}