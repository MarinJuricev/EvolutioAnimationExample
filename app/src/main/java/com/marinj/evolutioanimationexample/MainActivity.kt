package com.marinj.evolutioanimationexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Path
import android.view.animation.AccelerateInterpolator

class MainActivity : AppCompatActivity() {

    var animationState = State.STATE_INITIAL

    companion object {
        const val ANIMATION_DURATION: Long = 800L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            when (animationState) {
                State.STATE_ANIMATING -> return@setOnClickListener
                State.STATE_INITIAL -> startAnimation()
            }
        }
    }

    private fun startAnimation() {
        animationState = State.STATE_ANIMATING

        img.alpha = 1f
        img.visibility = View.VISIBLE

        val imgFadeIn = ObjectAnimator.ofFloat(img, View.ALPHA, 0f, 1f)
                .setDuration(ANIMATION_DURATION)

        val path = Path().apply {
            arcTo(0f, 0f, 1900f, fab.y + 50, 270f, -180f, true)
        }

        val yPathTransition = ObjectAnimator.ofFloat(img, View.X, View.Y, path)
                .setDuration(ANIMATION_DURATION)


        val fabAnimationY = ObjectAnimator.ofFloat(fab, "translationY", 5f)
                .setDuration(ANIMATION_DURATION)

        val fabAnimationX = ObjectAnimator.ofFloat(fab, "translationX", 35f)
                .setDuration(ANIMATION_DURATION)

        val imgAnimatorSet = AnimatorSet().apply {
            play(yPathTransition).with(imgFadeIn)
            interpolator = AccelerateInterpolator()
            start()
        }

        val animatorSet = AnimatorSet()

        imgAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animatorSet.apply {
                    play(fabAnimationY).with(fabAnimationX)
                    interpolator = ReverseInterpolator()
                    start()
                }
            }
        })

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationState = State.STATE_INITIAL
            }
        })
    }

    enum class State {
        STATE_INITIAL,
        STATE_ANIMATING,
    }
}
