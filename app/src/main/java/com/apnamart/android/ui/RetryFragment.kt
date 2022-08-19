package com.apnamart.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.apnamart.android.R
import com.apnamart.android.dataSource.RepositoryDb
import com.apnamart.android.dataSource.TrendingRepository
import com.apnamart.android.databinding.RetryLayoutBinding
import com.apnamart.android.utils.webservice
import com.apnamart.android.viewmodels.ParamViewModelFactory
import com.apnamart.android.viewmodels.TrendingViewModel

class RetryFragment : Fragment() {
    private lateinit var binding: RetryLayoutBinding
    private lateinit var viewmodel: TrendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.retry_layout, container, false)
        viewmodel = ViewModelProvider(
            requireActivity(),
            ParamViewModelFactory(
                TrendingRepository(
                    RepositoryDb(requireContext().applicationContext),
                    webservice
                )
            )
        )[TrendingViewModel::class.java]
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.appCompatButton.setOnClickListener {
            viewmodel.setData()
        }
        viewmodel.isErrorOccurred.observe(viewLifecycleOwner, Observer {
            if (!it) {
                requireActivity().findNavController(R.id.trending_container)
                    .navigate(R.id.trendingFragment)
            }
        })
    }
}