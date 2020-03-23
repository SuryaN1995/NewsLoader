package com.example.news.network

import com.example.news.network.model.NewsFeed
import com.example.news.utils.AppConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(AppConstants.APIKey.API_END)
    fun getFeeds() : Observable<NewsFeed>

    interface Callback {
        fun onSuccess(result: NewsFeed)
        fun onFailure(error: Throwable)
    }

}

