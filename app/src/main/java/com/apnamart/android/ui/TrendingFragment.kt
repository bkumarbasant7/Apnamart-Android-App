package com.apnamart.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apnamart.android.R
import com.apnamart.android.databinding.ContentLayoutBinding

class TrendingFragment : Fragment() {
    private lateinit var binding : ContentLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_layout,container,false)
        return binding.root
    }
}