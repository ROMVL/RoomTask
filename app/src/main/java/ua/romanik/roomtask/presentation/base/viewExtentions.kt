package ua.romanik.roomtask.presentation.base

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

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
    text = context.getString(value)
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
    val listener = createTextListener { if (isActive) offer(it) }
    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
}

fun createTextListener(emit: (CharSequence) -> Unit) = object : TextWatcher {
    override fun afterTextChanged(s: Editable) = Unit

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = emit(s)

}