package com.aldiprahasta.storyapp.presentation.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aldiprahasta.storyapp.databinding.ItemStoryBinding
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel

class StoryAdapter : ListAdapter<StoryDomainModel, StoryAdapter.StoryViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemStoryBinding.inflate(layoutInflater, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryDomainModel) {
            binding.apply {
                imgStory.load(item.photoUrl) {
                    crossfade(true)
                    placeholder(ColorDrawable(Color.GRAY))
                }
                tvStoryTitle.text = item.name
                tvStoryBody.text = item.description

                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(item, binding)
                }
            }
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(item: StoryDomainModel, view: ItemStoryBinding)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<StoryDomainModel>() {
            override fun areItemsTheSame(oldItem: StoryDomainModel, newItem: StoryDomainModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryDomainModel, newItem: StoryDomainModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}