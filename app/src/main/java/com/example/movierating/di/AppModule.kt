package com.example.movierating.di


import com.example.movierating.data_source.remote.response.MovieResponse
import com.example.movierating.data_source.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//App Module to add functions
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Function to provide retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit():MovieResponse = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieResponse::class.java)


}