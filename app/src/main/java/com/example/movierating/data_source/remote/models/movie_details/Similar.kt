package com.example.movierating.data_source.remote.models.movie_details

import com.example.movierating.data_source.remote.models.search_result.Result

data class Similar(
    val id: String,
    val imDbRating: String,
    val image: String,
    val title: String
)

