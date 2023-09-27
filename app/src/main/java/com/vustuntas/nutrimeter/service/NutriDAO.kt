package com.vustuntas.nutrimeter.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vustuntas.nutrimeter.model.Nutrition

@Dao
interface NutriDAO {
    @Insert
     fun insertAll(nutri : Nutrition) : Long

    @Query("SELECT * FROM Tbl_Nutritions")
     fun getAllNutrition() : List<Nutrition>


    @Query("SELECT * FROM Tbl_Nutritions WHERE ID = :foodId")
     fun getNutri(foodId : Int) : Nutrition

    @Query("DELETE FROM Tbl_Nutritions")
     fun deleteAllNutri()
}