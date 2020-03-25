package com.dror.mygallery.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dror.mygallery.R
import com.dror.mygallery.model.Photo
import com.dror.mygallery.utils.Utils


class GalleryAdapter(c: Activity, photos: List<Photo>, private val imageWidth: Int): BaseAdapter() {
    private var mContext: Activity = c
    private var mPhotos: List<Photo> = photos
    private val defaultImageWidthString = "_640"
    private val preferredImageWidthString = "_340"

    override fun getCount(): Int {
        return mPhotos.size
    }

    override fun getItem(position: Int): Any? {
        return mPhotos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var myView = convertView
        val holder: ViewHolder
        val photo = mPhotos[position]

        if (myView == null) {
            val mInflater = mContext.layoutInflater

            myView = mInflater.inflate(R.layout.photo, parent, false)

            holder = ViewHolder()

            holder.mGalleryImageView = myView?.findViewById(R.id.photoImageView)
            holder.mFavoriteImageView = myView?.findViewById(R.id.favoriteImageView)
            holder.view = myView
            holder.mFavoriteImageView?.setImageResource(R.drawable.ic_favorite)
            myView?.tag = holder
        } else {
            holder = myView.tag as ViewHolder
        }

        holder.mGalleryImageView!!.layoutParams = ConstraintLayout.LayoutParams(imageWidth, imageWidth)
        val imageURLInPreferredWidth = photo.webformatURL?.replace(defaultImageWidthString, preferredImageWidthString)
        Glide.with(mContext)
            .load(imageURLInPreferredWidth)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(holder.mGalleryImageView!!)

        val key = photo.id.toString()
        var isFavorite = Utils.getPreferences(mContext, key, false)
        if(isFavorite) {
            holder.mFavoriteImageView?.setImageResource(R.drawable.ic_favorite_fill)
        }
        else {
            holder.mFavoriteImageView?.setImageResource(R.drawable.ic_favorite)
        }
        holder.view?.setOnClickListener {
            isFavorite = Utils.getPreferences(mContext, key, false)
            if(isFavorite) {
                Utils.setPreferences(mContext, key, false)
                holder.mFavoriteImageView?.setImageResource(R.drawable.ic_favorite)
            }
            else {
                Utils.setPreferences(mContext, key, true)
                holder.mFavoriteImageView?.setImageResource(R.drawable.ic_favorite_fill)
            }
        }

        return myView
    }

    fun setPhotos(photos: List<Photo>) {
        mPhotos = photos
        notifyDataSetChanged()
    }

    class ViewHolder {
        var view: View? = null
        var mGalleryImageView: ImageView? = null
        var mFavoriteImageView: ImageView? = null

    }
}