package com.example.jokerfinder.base.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText

/**
 * Soft keyboard for search and done click.
 *
 * @param searching The action of search button in keyboard.
 */
fun EditText.imeSearchClick(searching: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            searching()
            true
        } else
            false
    }
}

/**
 * Used to listener of EditText when the user is typing text and cleaning it.
 *
 * @param doSomeThing Do somethings after text changed.
 */
fun EditText.textChangMonitor(doSomeThing: () -> Unit) {
    addTextChangedListener(
        object : TextWatcher {
            var change = false

            override fun afterTextChanged(p0: Editable?) {
                if (change)
                    doSomeThing()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                change = true
            }
        }
    )
}