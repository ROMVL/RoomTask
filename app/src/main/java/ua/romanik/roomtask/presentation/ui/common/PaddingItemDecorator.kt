package ua.romanik.roomtask.presentation.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

open class PaddingItemDecorator(
    context: Context,
    @DimenRes edgePadding: Int? = null,
    @DimenRes innerPadding: Int?,
    @DimenRes oppositeInnerPadding: Int?,
    @RecyclerView.Orientation
    val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {

    private val edgePadding = if (edgePadding != null) {
        context.resources.getDimensionPixelSize(edgePadding)
    } else {
        0
    }

    private val innerPadding = if (innerPadding != null) {
        context.resources.getDimensionPixelSize(innerPadding)
    } else {
        0
    }

    private val oppositeInnerPadding = if (oppositeInnerPadding != null) {
        context.resources.getDimensionPixelSize(oppositeInnerPadding)
    } else {
        -1
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val first = 0
        val last = (parent.adapter?.itemCount ?: 0) - 1

        if (orientation == RecyclerView.HORIZONTAL) {
            when (position) {
                first -> {
                    outRect.left = edgePadding
                    outRect.right = innerPadding
                }
                last -> {
                    outRect.left = innerPadding
                    outRect.right = edgePadding
                }
                else -> {
                    outRect.left = innerPadding
                    outRect.right = innerPadding
                }
            }
            if (oppositeInnerPadding != -1) {
                outRect.top = oppositeInnerPadding
                outRect.bottom = oppositeInnerPadding
            }
        } else {
            when (position) {
                first -> {
                    outRect.top = edgePadding
                    outRect.bottom = innerPadding
                }
                last -> {
                    outRect.top = innerPadding
                    outRect.bottom = edgePadding
                }
                else -> {
                    outRect.top = innerPadding
                    outRect.bottom = innerPadding
                }
            }
            if (oppositeInnerPadding != -1) {
                outRect.left = oppositeInnerPadding
                outRect.right = oppositeInnerPadding
            }
        }
    }
}