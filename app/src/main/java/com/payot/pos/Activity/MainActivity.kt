package com.payot.pos.Activity

import android.content.Intent
import android.os.Bundle
import com.payot.pos.Activity.Dialog.ProcessingDialog
import com.payot.pos.Activity.Dialog.ResultDialog
import com.payot.pos.App
import com.payot.pos.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtCompanyName.text = App.user?.name
        btnStart.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }
}
