package com.example.news.network

import com.example.news.utils.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {

    private var apiService: ApiService? = null

    private fun createApiService(): ApiService {

        val interceptorLog = HttpLoggingInterceptor()

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                request.method(original.method(), original.body())
                val requestBuilder = request.build()
                val response = chain.proceed(requestBuilder)

                response.newBuilder()
                    .body(
                        ResponseBody.create(
                            response.body()?.contentType(), response?.body()?.toString()
                                ?: ""
                        )
                    )
                    .build()
                response
            })
            interceptorLog.level = HttpLoggingInterceptor.Level.BODY
            this.addInterceptor(interceptorLog)
        }.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl(AppConstants.APIKey.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Synchronized
    fun getAPIService(): ApiService? {
        if (apiService == null) {
            apiService = createApiService()
        }
        return apiService
    }

    fun fetchFeeds() =
        getAPIService()?.getFeeds()?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread()
        )

}