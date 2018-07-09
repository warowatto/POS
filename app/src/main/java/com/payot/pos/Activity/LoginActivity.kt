package com.payot.pos.Activity

import android.content.Intent
import android.os.Bundle
import com.payot.pos.Activity.Dialog.CardInsertDialog
import com.payot.pos.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        btnLogin.setOnClickListener {
//            startActivity(Intent(this, ProductSettingActivity::class.java))
//        }

        btnLogin.setOnClickListener {
            showDialogFragment(CardInsertDialog())
        }
    }
}