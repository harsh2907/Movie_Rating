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
    private var _searchResults:MutableStateFlow<SearchResultState> = MutableStateFlow(SearchResultState())
    val searchResults = _searchResults.asStateFlow()

    private var _movieDetails:MutableStateFlow<MovieDetailsState> = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()

    fun getSearchResults(query:String){
        viewModelScope.launch {
            repository.searchMovie(query).collectLatest {  response->
                when(response){
                    is Response.Success ->{
                        _searchResults.value = searchResults.value.copy(
                            result = response.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    is Response.Loading->{
                        _searchResults.value = searchResults.value.copy(
                            result = response.data ?: emptyList(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
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


    fun getMovieDetails(id:String){
        viewModelScope.launch {
            repository.movieDetails(id).collectLatest {  response->
                when(response){
                    is Response.Success ->{
                        _movieDetails.value = movieDetails.value.copy(
                            result = response.data ?: MovieDetails(),
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    is Response.Loading->{
                        _movieDetails.value = movieDetails.value.copy(
                            result = response.data ?: MovieDetails(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
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