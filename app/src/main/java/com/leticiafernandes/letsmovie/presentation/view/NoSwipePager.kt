package com.leticiafernandes.letsmovie.presentation.view

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by leticiafernandes on 25/05/18.
 */
class NoSwipePager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {

    var swipeEnabled: Boolean = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (this.swipeEnabled) {
            return super.onTouchEvent(ev)
        }
        return false;
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.swipeEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }
}