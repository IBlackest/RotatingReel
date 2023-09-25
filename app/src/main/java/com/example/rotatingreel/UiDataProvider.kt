package com.example.rotatingreel

import android.content.Context
import androidx.core.content.ContextCompat

object UiDataProvider {

    fun provideData(context: Context, rotationAngle: Float): ColorData = when (rotationAngle) {
        in 0f..<SECTOR_ANGLE -> ColorData.WithText(
            "RED",
            ContextCompat.getColor(context, R.color.red)
        )

        in SECTOR_ANGLE..<SECTOR_ANGLE * 2 -> ColorData.WithImage("ORANGE", ORANGE_URI)
        in SECTOR_ANGLE * 2..<SECTOR_ANGLE * 3 -> ColorData.WithText(
            "YELLOW",
            ContextCompat.getColor(context, R.color.yellow)
        )

        in SECTOR_ANGLE * 3..<SECTOR_ANGLE * 4 -> ColorData.WithImage("GREEN", GREEN_URI)
        in SECTOR_ANGLE * 4..<SECTOR_ANGLE * 5 -> ColorData.WithText(
            "LIGHT BLUE",
            ContextCompat.getColor(context, R.color.light_blue)
        )

        in SECTOR_ANGLE * 5..<SECTOR_ANGLE * 6 -> ColorData.WithImage("DEEP BLUE", DEEP_BLUE_URI)
        else -> ColorData.WithText("PURPLE", ContextCompat.getColor(context, R.color.purple))
    }
}

private const val SECTOR_ANGLE = 360f / 7
private const val ORANGE_URI = "https://i.ibb.co/9r0S382/Orange.jpg/100x100bb.jpg"
private const val GREEN_URI = "https://i.ibb.co/Sw3yvJ7/Green.jpg/100x100bb.jpg"
private const val DEEP_BLUE_URI = "https://i.ibb.co/BCsb44m/DeepBlue.jpg/100x100bb.jpg"