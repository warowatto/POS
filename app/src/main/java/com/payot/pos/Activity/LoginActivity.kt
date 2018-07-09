package com.payot.pos.Activity

import android.os.Bundle
import com.payot.pos.App
import com.payot.pos.DI.Components.AppComponent
import com.payot.pos.DI.Components.DaggerLoginComponent
import com.payot.pos.DI.Modules.RestAPI
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : RootActivity() {

    @Inject
    lateinit var api: RestAPI

    @Inject
    lateinit var dao: AppDatabaseDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DaggerLoginComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        println(dao.getMachines())

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val pass = editEmail.text.toString()

            api.login(email, pass)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                println(it.first)
                                println(it.second)
                            },
                            {
                                it.printStackTrace()
                            }
                    )
        }
    }
}