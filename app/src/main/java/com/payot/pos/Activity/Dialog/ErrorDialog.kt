package com.payot.pos.Activity.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.payot.pos.R

class ErrorDialog : RootDialogFragment() {

    lateinit var rootView: View
    var message: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.dialog_error, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rootView.findViewById<TextView>(R.id.txtErrorReson).text = message
        rootView.findViewById<Button>(R.id.btnError).setOnClickListener {
            dialog.dismiss()
        }
    }
}