package com.example.movierating.data_source.models

import com.example.movierating.data_source.remote.models.movie_details.Actor
import com.example.movierating.data_source.remote.models.movie_details.Similar
import com.example.movierating.data_source.remote.models.search_result.Result

//Data class to map movie details into this class
data class MovieDetails(
    val id:String?=null,
    val movieName:String?="N/A",
    val releaseYear:String?="N/A",
    val releaseDate:String?="N/A",
    val contentRating:String?="N/A",
    val duration:String?="N/A",
    val thumbnail:String?="N/A",
    val imdbRating:String?="N/A",
    val genre:String?="N/A",
    val plot:String?="N/A",
    val director:String?="N/A",
    val stars:String?="N/A",
    val awards:String?="N/A",
    val similars:List<Similar>?= emptyList(),
    val casts:List<Actor>?= emptyList()
)

