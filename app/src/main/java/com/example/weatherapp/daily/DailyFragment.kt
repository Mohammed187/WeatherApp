package com.example.weatherapp.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyBinding

class DailyFragment : Fragment() {

    private val weatherViewModel: DailyViewModel by lazy {
        ViewModelProvider(this).get(DailyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDailyBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = weatherViewModel
            dailyRecyclerView.adapter = DailyItemsAdapter()
        }

        return binding.root
    }
}