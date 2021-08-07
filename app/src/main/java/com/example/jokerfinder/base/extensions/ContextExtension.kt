package com.example.jokerfinder.base.extensions

import android.content.Context
import android.content.Intent

const val TEXT_PLAIN = "text/plain"

/**
 * Used to popup share link for deep Link.
 *
 * @param deepLinkShare The route of URI which is made.
 */
fun Context.popUpShareLink(deepLinkShare: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, deepLinkShare)
        type = TEXT_PLAIN
    }
    startActivity(Intent.createChooser(sendIntent, null))
}
