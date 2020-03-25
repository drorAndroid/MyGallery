package com.dror.mygallery.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dror.mygallery.R
import com.dror.mygallery.model.Photo
import com.dror.mygallery.networking.GalleryNetworking
import com.dror.mygallery.networking.GalleryNetworkingAPI
import com.dror.mygallery.networking.GalleryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryRepository() {
    private var apiRequest: GalleryNetworkingAPI? = null
    private var currQuery = ""
    private var currPage: Int? = null
    val mPhotos: MutableLiveData<List<Photo>?> = MutableLiveData()
    val mLoading: MutableLiveData<Boolean> = MutableLiveData()

    val pageStartIndex = 1

    init {
        mLoading.value = false
        apiRequest = GalleryNetworking.getRetrofitInstance()?.create(GalleryNetworkingAPI::class.java)
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return mLoading
    }

    fun getPhotos(
        context: Context,
        query: String,
        perPage: Int
    ): MutableLiveData<List<Photo>?>? {
        val nextPage = getNextPage(query, perPage)
        if(currPage == null || currPage!! != nextPage) {
            mLoading.value = true
            currPage = nextPage
            apiRequest?.getPhotos(context.getString(R.string.pixabay_api_key), query, "photo", "popular", nextPage, perPage)
                ?.enqueue(object : Callback<GalleryResponse?> {
                    override fun onFailure(call: Call<GalleryResponse?>, t: Throwable) {
                        mPhotos.value = null
                        mLoading.value = false
                    }

                    override fun onResponse(
                        call: Call<GalleryResponse?>,
                        response: Response<GalleryResponse?>
                    ) {
                        Log.d("gallery_response", "onResponse response:: $response")
                        if (response.body() != null) {
                            val body = response.body()
                            body?.let {
                                body.photos?.let {
                                    val photos: List<Photo> = it
                                    var currList = mPhotos.value?.toMutableList()
                                    if (currList == null) {
                                        currList = mutableListOf()
                                    }
                                    currList.addAll(photos)
                                    mPhotos.value = currList
                                }
                            }
                        }

                        mLoading.value = false
                    }

                })
        }

        return mPhotos
    }

    private fun getNextPage(query: String, perPage: Int): Int {
        var nextPage = pageStartIndex
        if(query == currQuery) {
            if (mPhotos.value != null) {
                if (mPhotos.value!!.isNotEmpty()) {
                    nextPage = (mPhotos.value!!.size / perPage) + pageStartIndex
                }
            }
        }
        else {
            //the query has changed, invalidate current list andPage
            mPhotos.value = null
            currPage = null
        }
        currQuery = query

        return nextPage
    }

}