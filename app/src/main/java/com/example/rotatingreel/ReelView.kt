package com.example.rotatingreel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ReelView @JvmOverloads constructor (
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
    private var sweepAngle = 360f / colors.size
    private var startAngle = 90f

    override fun onDraw(canvas: Canvas) {
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        canvas.drawColor(Color.BLACK)

        side = if (viewWidth > viewHeight){
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
                startAngle, sweepAngle, true, paint)
            startAngle += sweepAngle
        }
    }
}
