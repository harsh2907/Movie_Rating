package com.example.movierating.ui.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movierating.data_source.remote.models.movie_details.Actor
import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.utils.Others
import com.example.movierating.databinding.ActivityMainBinding.bind
import com.example.movierating.databinding.ActivityMainBinding.inflate
import com.example.movierating.databinding.CastTemplateBinding


@SuppressLint("SetTextI18n")
class CastAdapter : RecyclerView.Adapter<CastAdapter.ResultViewHolder>() {
    inner class ResultViewHolder(val item: CastTemplateBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            CastTemplateBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)


    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        val item = differ.currentList[position]
        val binding = holder.item

        //Setting up actors actual name
        binding.actorActualName.text = item.name

        //Setting up actor's name in movie
        if (item.asCharacter.isBlank()) binding.actorName.visibility = View.INVISIBLE
        else binding.actorName.text = "as ${item.asCharacter}"

        //Setting up profile image of actors
        Glide.with(binding.root)
            .load(item.image)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(Others.getShimmer())
            .into(binding.actorImage)

    }

    override fun getItemCount() = differ.currentList.size

}