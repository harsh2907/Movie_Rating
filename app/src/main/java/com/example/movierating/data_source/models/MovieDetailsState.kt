package com.example.movierating.data_source.models

import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.utils.Response

//Data class to manage state
data class MovieDetailsState(
    val result:MovieDetails = MovieDetails(),
    val isLoading:Boolean = false,
    val errorMessage:String = ""
)
