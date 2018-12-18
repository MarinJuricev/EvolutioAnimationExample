package com.marinj.evolutioanimationexample

import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator

class ReverseInterpolator @JvmOverloads constructor(private val delegate: Interpolator = BounceInterpolator()) : Interpolator {

    override fun getInterpolation(input: Float): Float {
        return 1 - delegate.getInterpolation(input)
    }
}