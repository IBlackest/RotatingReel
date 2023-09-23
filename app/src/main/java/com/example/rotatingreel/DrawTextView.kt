package com.example.rotatingreel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class DrawTextView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint()
    var textColor = ContextCompat.getColor(context, R.color.black)
    var text: String = ""

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.CYAN)
        with(paint) {
            color = textColor
            isAntiAlias = true
            textSize = TEXT_SIZE
            setShadowLayer(5f, 10f, 10f, context.getColor(R.color.black))
        }
        canvas.drawText(text, 20f, 200f, paint)
    }

    companion object {
        private const val TEXT_SIZE = 50f
    }
}