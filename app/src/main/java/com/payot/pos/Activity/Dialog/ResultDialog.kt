package com.payot.pos.Activity.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.payot.pos.R
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ResultDialog : RootDialogFragment() {

    lateinit var button: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_error, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Single.just("")
                .delay(3, TimeUnit.SECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dialog.dismiss() },
                        { dialog.dismiss() }
                )
    }
}