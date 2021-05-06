package com.example.jokerfinder.base.extensions

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar

private const val STATUS_BAR_HEIGHT = "status_bar_height"
private const val DIMEN = "dimen"
private const val ANDROID = "android"

/**
 * Makes a view visible.
 *
 */
fun View.makeVisible() {
    visibility = View.VISIBLE
}

/**
 * Makes a view invisible.
 *
 */
fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

/**
 * Makes a view gone.
 *
 */
fun View.makeGone() {
    visibility = View.GONE
}

/**
 * Hides keyboard.
 *
 */
fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * shows keyboard.
 *
 */
fun View.showKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 1)
}

/**
 * Hides toggleSoftInput.
 *
 * @param context The context of view that hide toggle keyboard is to be done.
 */
fun View.hideToggleKeyboard(context: Context) {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

/**
 * Set padding for law text toolbar.
 *
 */
fun View.setLawTextToolbarPadding() {
    setPadding(0, getStatusBarHeight(resources), 0, 0)
}

/**
 * Makes a view clickable.
 *
 */
fun View.makeClickable() {
    isClickable = true
}

/**
 * Makes a view don't clickable.
 *
 */
fun View.makeNotClickable() {
    isClickable = false
}

/**
 * Set backGround Tint color
 */
fun View.setBackgroundTint(color: Int) {
    this.backgroundTintList = ColorStateList.valueOf(color)
}

/**
 * Handle search animation for open and close search box
 */
fun View.searchAnimation(isOpen: Boolean, imgDrawer: View, searchCard: View) {
    val circularConceal = if (!isOpen) {
        ViewAnimationUtils.createCircularReveal(
            searchCard,
            (imgDrawer.right + imgDrawer.left) / 2,
            (imgDrawer.top + imgDrawer.bottom) / 2,
            width.toFloat(),
            0f
        )
    } else {
        ViewAnimationUtils.createCircularReveal(
            searchCard,
            (imgDrawer.right + imgDrawer.left) / 2,
            (imgDrawer.top + imgDrawer.bottom) / 2,
            0f,
            width.toFloat()
        )
    }
    circularConceal.addListener(
        object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) {
                if (isOpen) searchCard.makeVisible()
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isOpen) {
                    searchCard.makeGone()
                    circularConceal.removeAllListeners()
                }
            }
        }
    )
    circularConceal.duration = 200
    circularConceal.start()
}

fun View.showDialog(
    message: String,
    buttonText: String? = null,
    buttonFunction: ((Snackbar) -> Unit)? = null
) {
    try {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackbar.view.setOnClickListener { snackbar.dismiss() }
        ViewCompat.setLayoutDirection(snackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        if (buttonText != null && buttonFunction != null)
            snackbar.setAction(buttonText) { buttonFunction(snackbar) }
        snackbar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Get status height.
 *
 * @param resources The resources of status bar height.
 **/
private fun getStatusBarHeight(resources: Resources): Int {
    val resourceId = resources.getIdentifier(STATUS_BAR_HEIGHT, DIMEN, ANDROID)
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}