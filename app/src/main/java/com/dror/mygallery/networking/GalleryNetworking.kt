package com.dror.mygallery.networking

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class GalleryNetworking {
    companion object {
        private val BASE_URL = "https://pixabay.com/"

        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                val dispatcher = Dispatcher(Executors.newFixedThreadPool(1))
                dispatcher.maxRequests = 1
                dispatcher.maxRequestsPerHost = 1

                val okHttpClient = OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
    }
}