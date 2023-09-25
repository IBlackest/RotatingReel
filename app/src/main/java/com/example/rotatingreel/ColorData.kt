package com.example.rotatingreel

sealed class ColorData {
    data class WithImage(val text: String, val imageUri: String) : ColorData()
    data class WithText(val text: String, val colorId: Int) : ColorData()
}