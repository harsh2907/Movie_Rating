package com.example.movierating.data_source.remote.response

import com.example.movierating.data_source.remote.models.movie_details.MovieDetailDTO
import com.example.movierating.data_source.remote.models.search_result.SearchResultDTO
import com.example.movierating.data_source.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieResponse {

    //Function to show movie details
    @GET("/API/Title/{apiKey}/{id}")
    suspend fun getMovieDetails(
        @Path("apiKey") apiKey:String = Constants.API_KEY,
        @Path("id") id:String
    ):MovieDetailDTO


    //Function to fetch data on searching movie
    @GET("/API/Search/{apiKey}/{query}")
    suspend fun getSearchDetails(
        @Path("apiKey") apiKey:String = Constants.API_KEY,
        @Path("query") query:String
    ):SearchResultDTO


}

