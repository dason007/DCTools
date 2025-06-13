package com.dason.dctools.extends

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.setNestedScrollingEnabled(): RecyclerView {
    return apply {
        isNestedScrollingEnabled = false
    }
}

fun RecyclerView.removeDefaultAnimator(): RecyclerView {
    return apply {
        itemAnimator = null
    }
}

fun RecyclerView.initLinearLayoutManager(orientation: Int = RecyclerView.VERTICAL): RecyclerView {
    return apply {
        val manager = LinearLayoutManager(context)
        manager.orientation = orientation
        manager.isSmoothScrollbarEnabled = true
        layoutManager = manager
    }
}

fun RecyclerView.initGridLayoutManager(
    spanCount: Int = 1,
    spanSizeLookup: GridLayoutManager.SpanSizeLookup? = null
): RecyclerView {
    return apply {
        val manager = GridLayoutManager(context, spanCount)
        manager.isSmoothScrollbarEnabled = true
        spanSizeLookup?.let {
            manager.spanSizeLookup = spanSizeLookup
        }
        layoutManager = manager
    }
}


fun RecyclerView.addItemDecoration(rect: Rect): RecyclerView {
    return apply {
        addItemDecoration(DividerItemDecoration { rect })
    }
}

fun RecyclerView.addItemDecoration(
    left: Int = 0,
    top: Int = 0,
    right: Int,
    bottom: Int
): RecyclerView {
    return apply {
        addItemDecoration(DividerItemDecoration {
            Rect(left, top, right, bottom)
        })
    }
}

fun RecyclerView.addItemDecoration(listener: ((position: Int) -> Rect)): RecyclerView {
    return apply {
        addItemDecoration(DividerItemDecoration(listener))
    }
}

class DividerItemDecoration constructor(@NonNull var mListener: ((position: Int) -> Rect)) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val rect = mListener.invoke(position)
        outRect.set(rect)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

    }
}
