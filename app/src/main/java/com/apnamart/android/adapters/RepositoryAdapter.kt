package com.apnamart.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.apnamart.android.R
import com.apnamart.android.databinding.ItemLayoutBinding
import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.utils.loadImage

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    inner class RepositoryViewHolder(val bind: ItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

    }

    interface ExpandListener {
        fun onExpand(position: Int)
    }

    private var callback: ExpandListener? = null
    fun setCallback(listener: ExpandListener) {
        callback = listener
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
        if(allData[position].author.isEmpty()){
            holder.bind.authorName.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.round_rect_bg))
            holder.bind.repoName.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.round_rect_bg))
        }else{
            holder.bind.authorName.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
            holder.bind.repoName.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
        }
        holder.bind.profilePic.loadImage(holder.itemView.context,allData[position].profilePic)
        holder.itemView.setOnClickListener {
            if(allData[position].author.isNotEmpty())
            updateMasterList(position)
        }
        holder.bind.executePendingBindings()
    }

    private fun updateMasterList(position: Int) {
        callback?.onExpand(position)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    fun setLoadView(it: Boolean?) {
        isLoading = it ?: false
    }
}