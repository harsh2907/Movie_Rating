package com.example.movierating.data_source.remote.models.movie_details

data class TvSeriesInfo(
    val creatorList: List<Creator>,
    val creators: String,
    val seasons: List<String>,
    val yearEnd: String
)