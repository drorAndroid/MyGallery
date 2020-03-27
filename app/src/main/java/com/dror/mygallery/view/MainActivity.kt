package com.dror.mygallery.view

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dror.mygallery.R
import com.dror.mygallery.adapter.GalleryAdapter
import com.dror.mygallery.model.Photo
import com.dror.mygallery.utils.Utils
import com.dror.mygallery.view_model.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GalleryViewModel
    private var galleryAdapter: GalleryAdapter? = null
    private val moreItemsThreshold = 20
    private val numOfColumnsPortrait = 3
    private val numOfColumnsLandscape = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        viewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchPhoto(newText)
                }
                return true
            }

        })
        galleryGridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int, visibleItemCount: Int,
                totalItemCount: Int
            ) {
                val lastItem = firstVisibleItem + visibleItemCount
                if ((lastItem >= totalItemCount - moreItemsThreshold) && totalItemCount != 0) {
                    // here you have reached end of list, load more data
                    viewModel.searchPhoto(viewModel.getCurrentQuery())
                }
            }

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            }
        })
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.getPhotosResponseLiveData()?.observe(this, Observer { photos ->
            setGalleryList(photos)
        })

        viewModel.getLoading().observe(this, Observer { loading ->
            if(loading) {
                progressBar.visibility = View.VISIBLE
            }
            else {
                progressBar.visibility = View.GONE
            }
        })
        viewModel.getError().observe(this, Observer { error ->
            error?.let {
                if(error.isNotEmpty()) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
            }
        })
    }

    private fun setGalleryList(photos: List<Photo>?) {
        var photosList = photos
        if(photosList == null) {
            photosList = listOf()
        }
        if (galleryAdapter == null) {
            galleryAdapter = GalleryAdapter(this@MainActivity, photosList, getPreferredImageWidth())
            galleryGridView.adapter = galleryAdapter

            galleryGridView.setOnItemClickListener { adapterView, parent, position, l ->
            }
        }
        else {
            galleryAdapter!!.setPhotos(photosList)
        }
    }

    private fun getPreferredImageWidth(): Int {
        val displayMetrics = Utils.getDisplayMetrics(this)

        return (displayMetrics.widthPixels - galleryGridView.numColumns* galleryGridView.horizontalSpacing)/ galleryGridView.numColumns
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            galleryGridView.numColumns = numOfColumnsLandscape
        }
        else {
            galleryGridView.numColumns = numOfColumnsPortrait
        }
    }
}
