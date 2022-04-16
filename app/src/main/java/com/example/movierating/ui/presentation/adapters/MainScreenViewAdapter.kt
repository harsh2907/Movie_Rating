package com.example.movierating.ui.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.utils.Others
import com.example.movierating.databinding.SearchResultTemplateBinding

//Recycle view adapter to show cast search results
class MainScreenViewAdapter : RecyclerView.Adapter<MainScreenViewAdapter.ResultViewHolder>() {
    inner class ResultViewHolder(val item: SearchResultTemplateBinding) :
        RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(

            SearchResultTemplateBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        val item = differ.currentList[position]
        val binding = holder.item
        binding.movieName.text = item.title

        Glide.with(binding.root)
            .load(item.image)
            .placeholder(Others.getShimmer())
            .into(binding.movieThumbnail)

        binding.resultCardView.setOnClickListener {
            onItemClicked?.let {
                it(item)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClicked: ((Result) -> Unit)? = null
    fun setOnItemClicked(listener: (Result) -> Unit) {
        onItemClicked = listener
    }
}