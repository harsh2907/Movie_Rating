package com.example.movierating.data_source.remote.models.movie_details

import com.example.movierating.data_source.models.MovieDetails

data class MovieDetailDTO(
    val actorList: List<Actor>,
    val awards: String,
    val companies: String,
    val contentRating: String,
    val directors:String,
    val errorMessage: String,
    val fullCast: String,
    val fullTitle: String,
    val genreList: List<Genre>,
    val genres: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingVotes: String,
    val image: String,
    val languages: String,
    val originalTitle: String,
    val plot: String,
    val releaseDate: String,
    val runtimeMins: String,
    val runtimeStr: String,
    val similars: List<Similar>,
    val stars: String,
    val title: String,
    val type: String,
    val year: String
)

fun MovieDetailDTO.toMovieDetails():MovieDetails{
    return MovieDetails(
        id = id,
        movieName = title,
        releaseYear = year,
        releaseDate = releaseDate,
        contentRating = contentRating,
        duration = runtimeStr,
        thumbnail = image,
        imdbRating = imDbRating,
        genre = genres,
        plot = plot,
        director = directors,
        stars = stars,
        awards = awards,
        similars = similars,
        casts = actorList
    )
}