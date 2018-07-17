package com.payot.pos.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Patterns
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerLoginComponent
import com.payot.pos.DI.Modules.LoginResponse
import com.payot.pos.DI.Modules.RestAPI
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Machine
import com.payot.pos.R
import com.payot.pos.Utils.observeOnMainThread
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface LoginController {
    fun bindView()

    fun login(email: String, password: String)

    fun errorDialog(message: String)

    fun sucess(response: LoginResponse)
}

class LoginActivity : RootActivity(), LoginController {

    @Inject
    lateinit var api: RestAPI

    @Inject
    lateinit var dao: AppDatabaseDAO

    @Inject
    lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DaggerLoginComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        bindView()

        Observable.just(preference)
                .filter { return@filter preference.getBoolean("auto", false) && preference.getString("email", "") != "" }
                .map { return@map preference.getString("email", "") to preference.getString("password", "") }
                .delay(1, TimeUnit.SECONDS)
                .observeOnMainThread()
                .subscribe(
                        { login(it.first, it.second) },
                        { it.printStackTrace() }
                ).addTo(dispose)
    }

    override fun bindView() {
        val emailValid = RxTextView.textChanges(editEmail).map { return@map Patterns.EMAIL_ADDRESS.matcher(it).matches() }
        val passwordValid = RxTextView.textChanges(editPass).map { return@map it.length >= 4 }

        Observable.combineLatest(emailValid, passwordValid, BiFunction { t1: Boolean, t2: Boolean -> return@BiFunction t1 && t2 })
                .subscribe { btnLogin.isEnabled = it }.addTo(dispose)

        RxView.clicks(btnLogin).subscribe({ login(editEmail.text.toString(), editPass.text.toString()) }, { it.printStackTrace() }).addTo(dispose)
    }

    override fun login(email: String, password: String) {

        val progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage("로그인 중 입니다")
        }

        progressDialog.show()
        api.login(email, password)
                .observeOnMainThread()
                .doOnEvent { sucess, error -> progressDialog.dismiss() }
                .subscribe({ sucess(it) }, {
                    if (it is HttpException) {
                        when (it.response().code()) {
                            400 -> {
                                errorDialog("비밀번호가 일치하지 않습니다")
                            }
                            404 -> {
                                errorDialog("존재하지 않는 계정입니다")
                            }
                        }
                    }
                })
    }

    override fun errorDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
                .setTitle("로그인 에러")
                .setMessage(message)
                .setNeutralButton("확인") { d, i -> }
                .create()

        dialog.show()
    }

    override fun sucess(response: LoginResponse) {
        App.user = response.user

        val editPreference = preference.edit()

        println("${preference.getString("email", "")} ${preference.getString("password", "")} ${preference.getBoolean("auth", false)}")
        if (!preference.getBoolean("auto", false)) {
            editPreference.putString("email", editEmail.text.toString()).commit()
            editPreference.putString("password", editPass.text.toString()).commit()
            editPreference.putBoolean("auto", true).commit()

            println("${preference.getString("email", "")} ${preference.getString("password", "")} ${preference.getBoolean("auth", false)}")
        }

        if (dao.getMachines().isEmpty()) {
            response.machines.forEach {
                val data = Machine(null, it.name, it.mac, it.type, it.info)
                dao.setMachine(data)
            }
        }

        if(dao.allProducts().isEmpty()) {
            val intent = Intent(this, ProductSettingActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        this.finish()
    }
}