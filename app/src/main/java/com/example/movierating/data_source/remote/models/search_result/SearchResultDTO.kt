package com.example.movierating.data_source.remote.models.search_result

data class SearchResultDTO(
    val errorMessage: String,
    val expression: String,
    val results: List<Result>?= emptyList(),
    val searchType: String
)