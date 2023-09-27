package com.vustuntas.nutrimeter.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vustuntas.nutrimeter.adapter.NutritionsAdapter
import com.vustuntas.nutrimeter.databinding.FragmentNutritionListBinding
import com.vustuntas.nutrimeter.viewmodel.NutririonListViewModel
import java.util.ArrayList

class NutritionList : Fragment() {
    private lateinit var _binding : FragmentNutritionListBinding
    private val binding get() = _binding.root

    private lateinit var nutritionListViewModel : NutririonListViewModel
    private lateinit var recyclerAdapter : NutritionsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNutritionListBinding.inflate(inflater,container,false)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.swipeRefreshLayout.setOnRefreshListener {
            _binding.errorMessageTextView.visibility = View.GONE
            _binding.foodListProgressBar.visibility = View.VISIBLE
            _binding.recyclerView.visibility = View.GONE
            nutritionListViewModel.refreshData()
            _binding.swipeRefreshLayout.isRefreshing = false
        }


        activity?.let{
            nutritionListViewModel = ViewModelProvider(it).get(NutririonListViewModel::class.java)
        }
        nutritionListViewModel.refreshData()

        observeLiveData()
        _binding.recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = NutritionsAdapter(ArrayList())
        _binding.recyclerView.adapter = recyclerAdapter
    }

    private fun observeLiveData(){
        nutritionListViewModel.nutritions.observe(viewLifecycleOwner, Observer {nutritions ->
            nutritions?.let {
                _binding.recyclerView.visibility = View.VISIBLE
                recyclerAdapter.refreshFoodList(it)
            }
        })

        nutritionListViewModel.nutritionsErrorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    _binding.errorMessageTextView.visibility = View.VISIBLE
                    _binding.recyclerView.visibility = View.INVISIBLE
                }
                else{
                    _binding.errorMessageTextView.visibility = View.GONE
                }
            }
        })

        nutritionListViewModel.nutriLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    _binding.recyclerView.visibility = View.GONE
                    _binding.errorMessageTextView.visibility = View.GONE
                    _binding.foodListProgressBar.visibility = View.VISIBLE
                }
                else{
                    _binding.foodListProgressBar.visibility = View.GONE

                }
            }
        })
    }

}