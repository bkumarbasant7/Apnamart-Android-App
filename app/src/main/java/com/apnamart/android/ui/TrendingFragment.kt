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
import com.apnamart.android.adapters.RepositoryAdapter
import com.apnamart.android.databinding.ContentLayoutBinding
import com.apnamart.android.viewmodels.TrendingViewModel

class TrendingFragment : Fragment() {
    private lateinit var binding: ContentLayoutBinding
    private lateinit var viewmodel: TrendingViewModel
    private lateinit var adapter: RepositoryAdapter
    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        viewmodel.initializeData(null)
        viewmodel.setData()
        viewmodel.isLoading.observe(viewLifecycleOwner, Observer {
            adapter.setLoadView(it)
            binding.refreshLayout.isRefreshing = it
        })
        viewmodel.trendingReposObservable.observe(this, Observer {
            adapter.setData(it)
            viewmodel.isLoading.postValue(false)
        })
        viewmodel.isErrorOccurred.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireActivity().findNavController(R.id.trending_container)
                    .navigate(R.id.retryFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_layout, container, false)
        viewmodel = ViewModelProvider(requireActivity())[TrendingViewModel::class.java]
        adapter = RepositoryAdapter()
        binding.trendingRepoRv.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            viewmodel.refresh()
        }

        return binding.root
    }
}