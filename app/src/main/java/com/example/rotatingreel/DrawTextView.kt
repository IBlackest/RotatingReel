package com.example.rotatingreel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class DrawTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint()
    var textColor = ContextCompat.getColor(context, R.color.black)
    var text: String = ""
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    private val rect = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight
        with(paint) {
            color = textColor
            isAntiAlias = true
            textSize = TEXT_SIZE
        }
        paint.getTextBounds(text, 0, text.length, rect)
        textWidth = paint.measureText(text)
        textHeight = rect.height().toFloat()
        canvas.drawText(text, viewWidth / 2f - textWidth / 2, viewHeight / 2f + textHeight / 2, paint)
    }

    companion object {
        private const val TEXT_SIZE = 70f
    }
}