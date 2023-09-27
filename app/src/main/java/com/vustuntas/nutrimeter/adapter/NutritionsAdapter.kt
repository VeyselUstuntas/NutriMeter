package com.vustuntas.nutrimeter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vustuntas.nutrimeter.model.Nutrition
import java.util.ArrayList
import com.vustuntas.nutrimeter.R
import com.vustuntas.nutrimeter.util.buildPlaceholder
import com.vustuntas.nutrimeter.util.downloadImage
import com.vustuntas.nutrimeter.view.fragment.NutritionList
import com.vustuntas.nutrimeter.view.fragment.NutritionListDirections

class NutritionsAdapter(val foodsArrayList:ArrayList<Nutrition>): RecyclerView.Adapter<NutritionsAdapter.NutriVh>() {
    class NutriVh(itemView : View): RecyclerView.ViewHolder(itemView){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutriVh {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_food_list,parent,false)
        return NutriVh(itemView)
    }

    override fun onBindViewHolder(holder: NutriVh, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.foodNameTextView).text = foodsArrayList.get(position).name
        holder.itemView.findViewById<TextView>(R.id.foodCalTextView).text = foodsArrayList.get(position).calorie
        //glide ile görsel gelecek
        holder.itemView.findViewById<ImageView>(R.id.imageView).downloadImage(foodsArrayList.get(position).image!!,
            buildPlaceholder(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
            val action = NutritionListDirections.actionNutritionListToNutritionDetail(foodsArrayList.get(position).ID)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return foodsArrayList.size
    }

    fun refreshFoodList(newFoodList : List<Nutrition> ){
        foodsArrayList.clear()
        foodsArrayList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}