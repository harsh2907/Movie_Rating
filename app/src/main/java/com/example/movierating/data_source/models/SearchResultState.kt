package com.example.movierating.data_source.models

import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.utils.Response

//Data class to manage search result
data class SearchResultState(
    val result:List<Result> = emptyList(),
    val isLoading:Boolean = false,
    val errorMessage:String = ""
)
