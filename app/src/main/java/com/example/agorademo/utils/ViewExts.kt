package com.example.agorademo.utils

import android.view.View
import android.view.ViewGroup


/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.invisible(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}

inline fun View.showOrhide(isVisible: () -> Boolean): View{
    visibility.apply {
        if(isVisible()) View.VISIBLE else View.GONE
    }
    return this
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    block: (T1, T2) -> R?,
): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun View.setHeight(heightInPixel: Int) {
    val tempLayoutParams = layoutParams as ViewGroup.LayoutParams
    tempLayoutParams.height = heightInPixel
    layoutParams = tempLayoutParams
}

fun View.setWidth(widthInPixel: Int) {
    val tempLayoutParams = layoutParams as ViewGroup.LayoutParams
    tempLayoutParams.width = widthInPixel
    layoutParams = tempLayoutParams
}
