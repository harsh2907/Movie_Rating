package com.example.movierating.data_source.remote.models.search_result

data class Result(
    val description: String,
    val id: String,
    val image: String,
    val resultType: String,
    val title: String
)