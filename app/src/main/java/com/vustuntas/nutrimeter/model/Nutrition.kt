package com.vustuntas.nutrimeter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Tbl_Nutritions")
data class Nutrition(
    @ColumnInfo(name = "food_name")
    @SerializedName("name")
    val name :String,

    @ColumnInfo(name = "food_calorie")
    @SerializedName("calorie")
    val calorie : String,

    @ColumnInfo(name = "food_carbohydrate")
    @SerializedName("carbohydrate")
    val carbohydrate : String,

    @ColumnInfo(name = "food_protein")
    @SerializedName("protein")
    val protein : String,

    @ColumnInfo(name = "food_fat")
    @SerializedName("fat")
    val fat : String,

    @ColumnInfo(name = "food_image")
    @SerializedName("image")
    val image : String
    )
{
    @PrimaryKey(autoGenerate = true)
    var ID = 0
}