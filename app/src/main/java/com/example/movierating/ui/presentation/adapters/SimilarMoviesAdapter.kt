package com.example.movierating.ui.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movierating.data_source.remote.models.movie_details.Similar
import com.example.movierating.data_source.utils.Others
import com.example.movierating.databinding.SimilarTemplateBinding

//Recycle view adapter to show similar results
@SuppressLint("SetTextI18n")
class SimilarMoviesAdapter:RecyclerView.Adapter<SimilarMoviesAdapter.SimilarViewHolder>() {

    inner class SimilarViewHolder(val item:SimilarTemplateBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            SimilarTemplateBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    private val diffUtil = object :DiffUtil.ItemCallback<Similar>(){
        override fun areItemsTheSame(oldItem: Similar, newItem: Similar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Similar, newItem: Similar): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {

        val item = differ.currentList[position]
        val binding = holder.item

        binding.tvMovieName.text = item.title

        Glide.with(binding.root)
            .load(item.image)
            .placeholder(Others.getShimmer())
            .into(binding.movieThumbnail)

        binding.tvIMDbRating.text = "Rating : ${item.imDbRating}/10"
        binding.movieCardView.setOnClickListener {
            onItemClicked?.let {
                it(item)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClicked:((Similar)->Unit)? = null
    fun setOnItemClicked(listener:(Similar)->Unit){
        onItemClicked = listener
    }
}