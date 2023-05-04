package com.dicoding.myreactivesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.myreactivesearch.network.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

class MainViewModel : ViewModel() {

    private val accessToken = "pk.eyJ1IjoibXVzc2hhbCIsImEiOiJjbGg5N2N1MHYwNG90M3FvMTl0MmZvNzZpIn0.kWbM20GnNTLCr3x61dP1ng"

    val queryChannel = MutableStateFlow("")
    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accessToken).features
        }
        .asLiveData()
}