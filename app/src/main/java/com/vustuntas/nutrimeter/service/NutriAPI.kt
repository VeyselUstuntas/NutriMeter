package com.vustuntas.nutrimeter.service

import android.database.Observable
import com.vustuntas.nutrimeter.model.Nutrition
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface NutriAPI {
    @GET("VeyselUstuntas/NutriMeter-JSONDataSet/main/nutritions.json")
    fun getData() : Single<List<Nutrition>>

}