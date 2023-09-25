package com.example.rotatingreel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class ReelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val colors: ArrayList<Int> = arrayListOf(
        ContextCompat.getColor(context, R.color.purple),
        ContextCompat.getColor(context, R.color.deep_blue),
        ContextCompat.getColor(context, R.color.light_blue),
        ContextCompat.getColor(context, R.color.green),
        ContextCompat.getColor(context, R.color.yellow),
        ContextCompat.getColor(context, R.color.orange),
        ContextCompat.getColor(context, R.color.red),
    )

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var side = 0
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private var sweepAngle = FULL_CIRCLE / colors.size
    private var startAngle = START_ANGLE

    override fun onDraw(canvas: Canvas) {
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        canvas.drawColor(Color.BLACK)

        side = if (viewWidth > viewHeight) {
            viewHeight
        } else {
            viewWidth
        }

        for (color in colors) {
            paint.color = color
            canvas.drawArc(
                ((viewWidth / 2) - (side / 2)).toFloat(),
                ((viewHeight / 2) - (side / 2)).toFloat(),
                ((viewWidth / 2) + (side / 2)).toFloat(),
                ((viewHeight / 2) + (side / 2)).toFloat(),
                startAngle, sweepAngle, true, paint
            )
            startAngle += sweepAngle
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val xPosition = event.x
        val yPosition = event.y
        val circleCenterX = this.measuredWidth / 2
        val circleCenterY = this.measuredHeight / 2

        //if (touchInCircle(xPosition, yPosition, circleCenterX, circleCenterY)) performClick()
        return touchInCircle(xPosition, yPosition, circleCenterX, circleCenterY)
    }

    private fun touchInCircle(
        xPosition: Float,
        yPosition: Float,
        circleCenterX: Int,
        circleCenterY: Int
    ): Boolean {
        val circleRadius = min(circleCenterX, circleCenterY)
        return (xPosition < circleCenterX + circleRadius
                && xPosition > circleCenterX - circleRadius
                && yPosition < circleCenterY + circleRadius
                && yPosition > circleCenterY - circleRadius)
    }


    companion object {
        private const val FULL_CIRCLE = 360f
        private const val START_ANGLE = 90f
    }
}
