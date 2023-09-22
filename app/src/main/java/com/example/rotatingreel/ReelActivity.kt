package com.example.rotatingreel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.RotateAnimation
import com.example.rotatingreel.databinding.ActivityReelBinding
import kotlin.random.Random

class ReelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReelBinding
    private var fromDegrees: Float? = null
    private var toDegrees: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reelView.setOnClickListener {
            rotate()
        }
    }

    private fun rotate() {
        val numberOfRotations = Random.nextInt(1, 100)
        val pivotX = binding.reelView.width / 2f
        val pivotY = binding.reelView.height / 2f
        fromDegrees = if (fromDegrees == null) 0f else toDegrees!! % FULL_CIRCLE
        toDegrees = (numberOfRotations * MIN_ROTATION_ANGLE)
        val animation = RotateAnimation(fromDegrees!!, toDegrees!!, pivotX, pivotY).apply {
            duration = DURATION
            fillAfter = true
            //interpolator = LinearOutSlowInInterpolator()
        }
        binding.reelView.startAnimation(animation)
    }

    companion object {
        private const val MIN_ROTATION_ANGLE = 360f / 7f
        private const val DURATION = 6000L
        private const val FULL_CIRCLE = 360f
    }
}