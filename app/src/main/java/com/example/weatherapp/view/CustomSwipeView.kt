package com.example.weatherapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs


@SuppressLint("ClickableViewAccessibility")
class CustomSwipeView constructor(context: Context?, attrs: AttributeSet?) : View(context, attrs), GestureDetector.OnGestureListener {

    private var gestureDetector: GestureDetector? = null


    init {
        setOnTouchListener { _, event ->
            gestureDetector?.onTouchEvent(event)
            Log.i("CustomSwipeView", "onTouchListener")
            true
        }
        gestureDetector = GestureDetector(getContext(), this)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {}

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        val distanceX = e2!!.x - e1!!.x
        val distanceY = e2.y - e1.y

        if (abs(distanceY) > abs(distanceX)){
            if (distanceY > 0) {
                Log.i("CustomSwipeView", "velocityY > 0")
            } else{
                Log.i("CustomSwipeView", "velocityY < 0")
            }
        }else{
            if (distanceX > 0) {
                Log.i("CustomSwipeView", "velocityX > 0")
            } else{
                Log.i("CustomSwipeView", "velocityX < 0")
            }
        }

        invalidate()
        return true
    }

}
