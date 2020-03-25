package com.dror.mygallery.networking

import com.dror.mygallery.model.Photo
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class GalleryResponse {
    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("totalHits")
    @Expose
    var totalHits: Int? = null

    @SerializedName("hits")
    @Expose
    var photos: List<Photo>? = null
}