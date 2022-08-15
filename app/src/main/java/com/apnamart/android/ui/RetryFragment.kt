package com.apnamart.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apnamart.android.R
import com.apnamart.android.databinding.RetryLayoutBinding

class RetryFragment : Fragment() {
    private lateinit var binding: RetryLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.retry_layout, container, false)
        return binding.root
    }
}