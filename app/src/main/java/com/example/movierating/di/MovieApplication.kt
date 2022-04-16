package com.example.movierating.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Provided application required for dagger hilt
@HiltAndroidApp
class MovieApplication:Application()