package ua.romanik.roomtask.presentation.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visibleIf(predicate: () -> Boolean) {
    if (predicate.invoke()) visible() else gone()
}

fun View.goneIf(predicate: () -> Boolean) {
    if (predicate.invoke()) gone() else visible()
}

fun TextView.initText(@StringRes value: Int) {
    setText(value)
}

fun TextView.initText(value: String) {
    text = value
}

fun TextView.initText(value: Number) {
    text = value.toString()
}

@ExperimentalCoroutinesApi
fun TextView.textChanges(): Flow<CharSequence> = channelFlow {
    offer(text)
    val listener = addTextChangedListener {
        if (isActive) offer(it?.toString() ?: "")
    }
    awaitClose { removeTextChangedListener(listener) }
}