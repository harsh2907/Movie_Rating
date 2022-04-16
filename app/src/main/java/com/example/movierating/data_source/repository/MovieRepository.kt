package com.example.movierating.data_source.repository

import android.app.DownloadManager
import com.example.movierating.data_source.models.MovieDetails
import com.example.movierating.data_source.models.MovieDetailsState
import com.example.movierating.data_source.remote.models.movie_details.MovieDetailDTO
import com.example.movierating.data_source.remote.models.movie_details.toMovieDetails
import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.remote.models.search_result.SearchResultDTO
import com.example.movierating.data_source.remote.response.MovieResponse
import com.example.movierating.data_source.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api:MovieResponse
) {

    fun searchMovie(query: String): Flow<Response<List<Result>>> = flow{
       try {
           emit(Response.Loading())
           val response = api.getSearchDetails(query = query)
           when {
               response.results?.isEmpty() == true -> {
                   emit(Response.Error(message = "Oops,No Result Found..."))
               }
               response.errorMessage.isNotBlank() -> {
                   emit(Response.Error(message = response.errorMessage))
               }
               else -> {
                   emit(Response.Success(data = response.results!!))
               }
           }
       }catch (e: HttpException){
            emit(Response.Error(message = "Oops, something went wrong"))
        }
        catch (e: IOException){
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
         }

    }

    fun movieDetails(id:String): Flow<Response<MovieDetails>> = flow{
        emit(Response.Loading())
        try {
            val response = api.getMovieDetails(id = id).toMovieDetails()
            if (response.imdbRating.isNullOrBlank() || response.casts.isNullOrEmpty()){
                emit(Response.Error(message = "Sorry,no details found"))
            }
           else emit(Response.Success(data = response ))
        }catch (e: HttpException){
            emit(Response.Error(message = "Oops, something went wrong"))
        }
        catch (e: IOException){
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
        }

    }

}