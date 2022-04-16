package com.example.movierating.ui.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierating.data_source.models.MovieDetails
import com.example.movierating.data_source.models.MovieDetailsState
import com.example.movierating.data_source.models.SearchResultState
import com.example.movierating.data_source.remote.models.movie_details.Similar
import com.example.movierating.data_source.remote.models.search_result.Result
import com.example.movierating.data_source.repository.MovieRepository
import com.example.movierating.data_source.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: MovieRepository
):ViewModel(){
    //Variable to manage search results
    private var _searchResults:MutableStateFlow<SearchResultState> = MutableStateFlow(SearchResultState())
    val searchResults = _searchResults.asStateFlow()

    //Variable to manage movie details
    private var _movieDetails:MutableStateFlow<MovieDetailsState> = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()

    //Function to provide search result
    fun getSearchResults(query:String){
        viewModelScope.launch {
            repository.searchMovie(query).collectLatest {  response->
                when(response){
                    //if response is successful
                    is Response.Success ->{
                        _searchResults.value = searchResults.value.copy(
                            result = response.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    //if response is loading
                    is Response.Loading->{
                        _searchResults.value = searchResults.value.copy(
                            result = response.data ?: emptyList(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    //if response got an error
                    is Response.Error->{
                        _searchResults.value = searchResults.value.copy(
                            result = response.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = response.message ?: "An Unexpected Error Occurred"
                        )
                    }
                }
            }
        }

    }

    //Function to get movie details
    fun getMovieDetails(id:String){
        viewModelScope.launch {
            repository.movieDetails(id).collectLatest {  response->
                when(response){
                    //if response is successful
                    is Response.Success ->{
                        _movieDetails.value = movieDetails.value.copy(
                            result = response.data ?: MovieDetails(),
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    //if response is loading
                    is Response.Loading->{
                        _movieDetails.value = movieDetails.value.copy(
                            result = response.data ?: MovieDetails(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    //if response got an error
                    is Response.Error->{
                        _movieDetails.value = movieDetails.value.copy(
                            result = response.data ?: MovieDetails(),
                            isLoading = false,
                            errorMessage = response.message ?: "An Unexpected Error Occurred"
                        )
                    }
                }
            }
        }

    }



}