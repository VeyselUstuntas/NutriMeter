package com.vustuntas.nutrimeter.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.vustuntas.nutrimeter.R
import com.vustuntas.nutrimeter.databinding.FragmentNutritionDetailBinding
import com.vustuntas.nutrimeter.databinding.FragmentNutritionListBinding
import com.vustuntas.nutrimeter.util.buildPlaceholder
import com.vustuntas.nutrimeter.util.downloadImage
import com.vustuntas.nutrimeter.viewmodel.NutritionDetailViewModel


class NutritionDetail : Fragment() {
    private lateinit var _binding : FragmentNutritionDetailBinding
    private val binding get() = _binding.root
    private var foodId : Int? = null
    private lateinit var nutritionDetailViewModel : NutritionDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNutritionDetailBinding.inflate(inflater,container,false)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId = NutritionDetailArgs.fromBundle(it).foodId
        }

        activity?.let {
            nutritionDetailViewModel = ViewModelProvider(it).get(NutritionDetailViewModel::class.java)
        }
        if(foodId != null){
            nutritionDetailViewModel.getRoomData(foodId!!)
            obserseLiveData()
        }
    }

    private fun obserseLiveData(){
        nutritionDetailViewModel.nutritions.observe(viewLifecycleOwner, Observer {
            it?.let {
                _binding.nutritionDetailFoodNameTextView.text = it.name
                _binding.nutritionDetailFoodCaloriTextView.text = it.calorie
                _binding.nutritionDetailFoodCarbohydrateTextView.text = it.carbohydrate
                _binding.nutritionDetailFoodProteinTextView.text = it.protein
                _binding.nutritionDetailFoodFatTextView.text = it.fat
                context?.let {contextAc->
                    _binding.foodImageView.downloadImage(it.image, buildPlaceholder(contextAc))
                }
            }
        })
    }
}