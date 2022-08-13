package com.gigfa.ariya.news.dataLayer

import com.gigfa.ariya.news.model.ModelSearchResponse
import com.gigfa.ariya.news.model.ModelTrendsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("top-headlines")
    @Headers("accept:application/json", "Content-Type: application/json")
    fun getTrends(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("sources") sources: String
    ): Call<ModelTrendsResponse>

    @GET("everything")
    @Headers("accept:application/json", "Content-Type: application/json")
    fun getSearch(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
        @Query("sortBy") category: String
    ): Call<ModelSearchResponse>

}
