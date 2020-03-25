package com.dror.mygallery.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GalleryNetworking {
    companion object {
        private val BASE_URL = "https://pixabay.com/"

        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}