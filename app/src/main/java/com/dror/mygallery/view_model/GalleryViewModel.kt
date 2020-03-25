package com.dror.mygallery.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dror.mygallery.model.Photo
import com.dror.mygallery.repository.GalleryRepository


class GalleryViewModel(app: Application): AndroidViewModel(app) {
    private var galleryRepository: GalleryRepository = GalleryRepository()
    private var galleryResponseLiveData: MutableLiveData<List<Photo>?>? = null

    private var photosPerPage: Int = 100


    init {
        searchPhoto("")
    }

    fun searchPhoto(query: String) {
        galleryResponseLiveData =
            galleryRepository.getPhotos(getApplication(),query, photosPerPage)
    }

    fun getPhotosResponseLiveData(): LiveData<List<Photo>?>? {
        return galleryResponseLiveData
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return galleryRepository.getLoading()
    }
}