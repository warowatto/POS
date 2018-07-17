package com.payot.pos.Activity.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.payot.pos.R

open class RootDialogFragment : DialogFragment() {

    val flag = View.SYSTEM_UI_FLAG_IMMERSIVE or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        dialog.window.decorView.setOnSystemUiVisibilityChangeListener {
            if ((it and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                dialog.window.decorView.systemUiVisibility = flag
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        dialog.window.setLayout(
                resources.getDimensionPixelSize(R.dimen.dialog_width),
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}