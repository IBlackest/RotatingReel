package com.example.rotatingreel

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.rotatingreel.databinding.ActivityReelBinding
import kotlin.random.Random

class ReelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReelBinding
    private lateinit var drawText: DrawTextView
    private var fromDegrees: Float? = null
    private var toDegrees: Float? = null
    private var rotationAngle = 0f

    private var animationListener = object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }
        override fun onAnimationEnd(p0: Animation?) {
            calculateResult(rotationAngle)
        }
        override fun onAnimationRepeat(p0: Animation?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.reelView.setOnClickListener {
            rotate()
        }
        initDrawText()
    }

    private fun rotate() {
        val pivotX = binding.reelView.width / 2f
        val pivotY = binding.reelView.height / 2f
        fromDegrees = if (fromDegrees == null) 0f else toDegrees!! % FULL_CIRCLE
        toDegrees = Random.nextInt(
            1,
            FULL_CIRCLE.toInt() * MAX_ROTATIONS_NUMBER - FULL_CIRCLE.toInt()
        ) + FULL_CIRCLE
        val animation = RotateAnimation(fromDegrees!!, toDegrees!!, pivotX, pivotY).apply {
            duration = DURATION
            fillAfter = true
            setAnimationListener(animationListener)
            //interpolator = LinearOutSlowInInterpolator()
        }
        binding.reelView.startAnimation(animation)
        rotationAngle = (rotationAngle + toDegrees!! - fromDegrees!!) % FULL_CIRCLE
    }

    private fun calculateResult(rotationAngle: Float) {
        when (rotationAngle) {
            in 0f..<SECTOR_ANGLE -> renderText(TextEnum.RED)
            in SECTOR_ANGLE..<SECTOR_ANGLE * 2 -> renderImage(ImageEnum.ORANGE)
            in SECTOR_ANGLE * 2..<SECTOR_ANGLE * 3 -> renderText((TextEnum.YELLOW))
            in SECTOR_ANGLE * 3..<SECTOR_ANGLE * 4 -> renderImage((ImageEnum.GREEN))
            in SECTOR_ANGLE * 4..<SECTOR_ANGLE * 5 -> renderText((TextEnum.LIGHT_BLUE))
            in SECTOR_ANGLE * 5..<SECTOR_ANGLE * 6 -> renderImage((ImageEnum.DEEP_BLUE))
            in SECTOR_ANGLE * 6..<SECTOR_ANGLE * 7 -> renderText((TextEnum.PURPLE))
        }
    }

    private fun renderText(color: TextEnum) {
        when (color) {
            TextEnum.RED -> {
                drawText.textColor = ContextCompat.getColor(this, R.color.red)
                drawText.text = this.toString()
            }
            TextEnum.YELLOW -> {
                drawText.textColor = ContextCompat.getColor(this, R.color.yellow)
                drawText.text = this.toString()
            }
            TextEnum.LIGHT_BLUE -> {
                drawText.textColor = ContextCompat.getColor(this, R.color.light_blue)
                drawText.text = this.toString()
            }
            TextEnum.PURPLE -> {
                drawText.textColor = ContextCompat.getColor(this, R.color.purple)
                drawText.text = this.toString()
            }
        }
        drawText.invalidate()
    }

    private fun renderImage(color: ImageEnum) {
        val imageUri = when (color) {
            ImageEnum.ORANGE -> ORANGE_URI
            ImageEnum.GREEN -> GREEN_URI
            ImageEnum.DEEP_BLUE -> DEEP_BLUE_URI
        }
        Glide.with(this)
            .load(imageUri)
            .transform(CenterCrop())
            .into(binding.image)
        binding.image.invalidate()
    }

    private fun initDrawText() {
        val constraintSet = ConstraintSet()
        with(constraintSet) {
            clone(binding.root)
        }
        drawText = DrawTextView(this).apply {
            text = "TESTTESTTESTTESTTEST"
            layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                startToStart = R.id.parent
                endToEnd = R.id.parent
                bottomToTop = R.id.image
            }
        }
        binding.root.addView(drawText)
    }

    enum class ImageEnum {
        ORANGE, GREEN, DEEP_BLUE
    }

    enum class TextEnum {
        RED, YELLOW, LIGHT_BLUE, PURPLE
    }

    companion object {
        private const val SECTOR_ANGLE = 360f / 7
        private const val DURATION = 6000L
        private const val FULL_CIRCLE = 360f
        private const val MAX_ROTATIONS_NUMBER = 10
        private const val ORANGE_URI = "https://i.ibb.co/9r0S382/Orange.jpg/100x100bb.jpg"
        private const val GREEN_URI = "https://i.ibb.co/Sw3yvJ7/Green.jpg/100x100bb.jpg"
        private const val DEEP_BLUE_URI = "https://i.ibb.co/BCsb44m/DeepBlue.jpg/100x100bb.jpg"
    }
}