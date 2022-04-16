package com.example.movierating.data_source.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.movierating.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.chip.Chip

object Others {

    //Shimmer effect for loading
    fun getShimmer(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1000) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(1f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        // This is the placeholder for the imageView
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        return shimmerDrawable
    }

    //Function to create chip dynamically
    fun createChip(context: Context, chipName: String) = Chip(context).apply { text = chipName }



}