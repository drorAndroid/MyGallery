package com.dror.mygallery.networking

import com.dror.mygallery.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GalleryNetworkingAPI {
    @GET("api/")
    fun getPhotos(
        @Query("key") apiKey: String?,
        @Query("q") query: String?
        ): Call<GalleryResponse?>?

    @GET("api/")
    fun getPhotos(
        @Query("key") apiKey: String?,
        @Query("q") query: String?,
        @Query("image_type") imageType: String?,
        @Query("order") order: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<GalleryResponse?>?

    @GET("api/")
    fun searchPhoto(
        @Query("key") apiKey: String?,
        @Query("lang") lang: String?,
        @Query("id") id: String?,
        @Query("category") category: String?,
        @Query("min_width") minWidth: Int?,
        @Query("min_height") minHeight: Int,
        @Query("editors_choice") editorsChoice: Boolean?,
        @Query("safesearch") safeSearch: Boolean?,
        @Query("order") order: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("pretty") pretty: Boolean?,
        @Query("image_type") imageType: String?,
        @Query("orientation") orientation: String?
    ): Call<GalleryResponse?>?
}