package com.vustuntas.nutrimeter.service

import android.database.Observable
import android.telephony.PhoneNumberFormattingTextWatcher
import com.vustuntas.nutrimeter.model.Nutrition
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NutriAPIService {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NutriAPI::class.java)

    fun getAllData() : Single<List<Nutrition>>{
        return api.getData()
    }


}