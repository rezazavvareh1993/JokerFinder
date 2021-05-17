package com.example.jokerfinder.base.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Loads images into ImageViews.
 *
 * @param url The route url of image response.
 * @param placeholder The placeholder of image in view.
 */
fun ImageView.loadUrl(url: String, placeholder: Int? = null) {
    val picasso = Glide.with(context).load(url)
    placeholder?.let { picasso.placeholder(it) }
    picasso.into(this)
}

/**
 * Extension function to show staggered images correctly with picasso.
 *
 * @param url The route url of staggered image response.
 * @param placeholder The placeholder of staggered image in view.
 */
fun ImageView.loadStaggeredImage(url: String, placeholder: Int? = null) {
    val picasso = Glide.with(context).load(url).fitCenter()
    placeholder?.let { picasso.placeholder(it) }
    picasso.into(this)
}