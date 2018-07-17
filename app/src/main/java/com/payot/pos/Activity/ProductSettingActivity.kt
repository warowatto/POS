package com.payot.pos.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.payot.pos.Activity.Fragment.MachineListFragment
import com.payot.pos.Activity.Fragment.ProductSettingFragment
import com.payot.pos.App
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Machine
import com.payot.pos.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_product_setting.*
import javax.inject.Inject

class ProductSettingActivity : RootActivity() {

    @Inject
    lateinit var dao: AppDatabaseDAO

    val selectMachine: PublishSubject<Machine> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_setting)

        companyBinding()
        fragmentBinding()

        btnComplite.setOnClickListener {
            agreeDialog()
        }
    }

    private fun agreeDialog() {
        AlertDialog.Builder(this)
                .setTitle("상품등록 완료")
                .setMessage("상품등록을 완료하시겠습니까?")
                .setPositiveButton("등록완료") { d, i -> startSales() }
                .setNegativeButton("취소") { d, i -> }
                .show()
    }

    private fun startSales() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun companyBinding() {
        val user = App.user
        txtCompanyName.text = user?.name
        txtCompanyNumber.text = user?.companyNumber
        txtOwnerName.text = user?.ownerName
    }

    fun fragmentBinding() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentMachineList, MachineListFragment(), "machine_list").commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentProductSetting, ProductSettingFragment(), "product_list").commit()
    }
}