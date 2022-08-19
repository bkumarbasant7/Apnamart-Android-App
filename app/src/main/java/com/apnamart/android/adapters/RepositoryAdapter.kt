package com.apnamart.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.apnamart.android.R
import com.apnamart.android.databinding.ItemLayoutBinding
import com.apnamart.android.models.RepositoryModel

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    inner class RepositoryViewHolder(val bind: ItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

    }


    private val allData: MutableList<RepositoryModel> = mutableListOf()
    private var isLoading = false
    private var expandedAt: Int = -1


    fun setData(newData: List<RepositoryModel>) {
        allData.clear()
        allData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, pos: Int) {
        val position = pos
        holder.bind.isLoaded = !isLoading
        holder.bind.isExpanded = allData[position].isExpanded
        holder.bind.repo = allData[position]
        holder.bind.isLoaded = !isLoading
        holder.itemView.setOnClickListener {
            updateMasterList(position)
        }
        holder.bind.executePendingBindings()
    }

    private fun updateMasterList(position: Int) {
        if (expandedAt == -1) {
            allData[position].isExpanded = true
            expandedAt = position
            notifyItemChanged(position)

        } else if (expandedAt >= 0) {
            if (expandedAt == position) {
                allData[expandedAt].isExpanded = false
                notifyItemChanged(expandedAt)
                expandedAt = -1
            } else {
                if (allData[expandedAt].isExpanded) {
                    allData[expandedAt].isExpanded = false
                    notifyItemChanged(expandedAt)
                    allData[position].isExpanded = true
                    notifyItemChanged(position)
                    expandedAt = position
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    fun setLoadView(it: Boolean?) {
        isLoading = it ?: false
    }
}