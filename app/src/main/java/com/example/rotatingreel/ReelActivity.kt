package com.example.rotatingreel

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.RotateAnimation
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.constraintlayout.widget.ConstraintSet
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
    private var reelWidth = 0
    private var reelHeight = 0
    private lateinit var reelParams: LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reelParams = binding.reelView.layoutParams as LayoutParams
        binding.reelView.setOnClickListener {
            rotate()
        }
        initDrawText()
        initSlider()
        binding.reset.setOnClickListener {
            clearText()
            clearImage()
        }
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
            setAnimationListener(
                object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        renderUi(UiDataProvider.provideData(applicationContext, rotationAngle))
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                }
            )
        }
        binding.reelView.startAnimation(animation)
        rotationAngle = (rotationAngle + toDegrees!! - fromDegrees!!) % FULL_CIRCLE
    }

    private fun renderUi(color: ColorData) {
        when (color) {
            is ColorData.WithText -> renderText(color.colorId, color.text)
            is ColorData.WithImage -> renderImage(color.imageUri)
        }
    }

    private fun renderText(colorId: Int, colorText: String) {
        drawText.textColor = colorId
        drawText.text = colorText
        drawText.invalidate()
        clearImage()
    }

    private fun renderImage(uri: String) {
        Glide.with(this)
            .load(uri)
            .transform(CenterCrop())
            .into(binding.image)
        clearText()
    }

    private fun initDrawText() {
        val constraintSet = ConstraintSet()
        with(constraintSet) {
            clone(binding.root)
        }
        drawText = DrawTextView(this).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200
            )
            updateLayoutParams<LayoutParams> {
                startToStart = R.id.parent
                endToEnd = R.id.parent
                bottomToTop = R.id.image
            }
        }
        binding.root.addView(drawText)
    }

    private fun initSlider() {
        val min = 0
        val max = 100
        var current = 50
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.slider.min = min
            binding.slider.max = max
        }
        binding.slider.progress = current
        binding.slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                changeReelSize(progress - current)
                current = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun changeReelSize(progress: Int) {
        reelWidth = binding.reelView.width
        reelHeight = binding.reelView.height
        reelParams.width = reelWidth + progress * 4
        reelParams.height = reelHeight + progress * 4
        binding.reelView.layoutParams = reelParams
    }

    private fun clearText() {
        drawText.text = ""
        drawText.invalidate()
    }

    private fun clearImage() {
        Glide.with(this).clear(binding.image)
    }

    companion object {
        private const val DURATION = 6000L
        private const val FULL_CIRCLE = 360f
        private const val MAX_ROTATIONS_NUMBER = 10
    }
}