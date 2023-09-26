package com.example.rotatingreel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import kotlin.random.Random

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

    var rotationAngle = 0f
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var toDegrees: Float? = null
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
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.isClickable = false
                toDegrees = Random.nextInt(
                    1,
                    FULL_CIRCLE.toInt() * MAX_ROTATIONS_NUMBER - FULL_CIRCLE.toInt()
                ) + FULL_CIRCLE
                rotationAngle = (rotationAngle + toDegrees!!) % FULL_CIRCLE
                this.animate()
                    .rotationBy(toDegrees!!)
                    .setDuration(DURATION)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private const val FULL_CIRCLE = 360f
        private const val START_ANGLE = 90f
        private const val DURATION = 5000L
        private const val MAX_ROTATIONS_NUMBER = 30
    }
}
