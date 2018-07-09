package com.payot.pos.Activity

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View

open class RootActivity : AppCompatActivity() {

    val flag = View.SYSTEM_UI_FLAG_IMMERSIVE or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.setOnSystemUiVisibilityChangeListener {
            if ((it and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                window.decorView.systemUiVisibility = flag
            }
        }


    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) window.decorView.systemUiVisibility = flag
    }

    public fun showDialogFragment(fragment: DialogFragment) {
        fragment.show(supportFragmentManager, "dialog")
    }
}