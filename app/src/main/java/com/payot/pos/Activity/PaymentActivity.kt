package com.payot.pos.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer
import com.payot.pos.Activity.Dialog.ErrorDialog
import com.payot.pos.Activity.Dialog.ProcessingDialog
import com.payot.pos.Activity.Dialog.ResultDialog
import com.payot.pos.Activity.Fragment.MachineSelectFragment
import com.payot.pos.Activity.Fragment.ProductSelectFragment
import com.payot.pos.Activity.Fragment.SelectResultFragment
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerPaymentComponent
import com.payot.pos.DI.Modules.AppPOSPay
import com.payot.pos.DI.Modules.PaymentModule
import com.payot.pos.DI.Modules.RestAPI
import com.payot.pos.Data.Database.Machine
import com.payot.pos.Data.Database.Product
import com.payot.pos.R
import com.payot.pos.Utils.formatDate
import com.payot.pos.Utils.observeOnMainThread
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.*
import javax.inject.Inject

class PaymentActivity : RootActivity() {

    @Inject
    lateinit var api: RestAPI

    val titleLabel: Array<String> = arrayOf("Step 1 : 사용하실 장비를 선택해주세요", "Step 2 : 서비스 종류 / 시간을 선택해 주세요", "Step 3 : 서비스와 결제금액을 확인해 주세요")

    val fragments: Array<Fragment> = arrayOf(
            MachineSelectFragment(),
            ProductSelectFragment(),
            SelectResultFragment()
    )

    var machine: Machine? = null
    var product: Product? = null

    data class ActivityResult(val resultCode: Int, val requestCode: Int, val intent: Intent)

    val payResult: PublishSubject<ActivityResult> = PublishSubject.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        DaggerPaymentComponent.builder()
                .appComponent(App.component)
                .paymentModule(PaymentModule(this@PaymentActivity))
                .build().inject(this)

        viewpager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager)
            setPageTransformer(true, ZoomOutSlideTransformer())
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    txtTitle.text = titleLabel[position]

                    btnPayment.visibility = View.GONE
                    if (position == 2) btnPayment.visibility = View.VISIBLE
                }

            })
        }

        btnPrev.setOnClickListener {
            pageChange(viewpager.currentItem - 1)
        }

        btnPayment.setOnClickListener {
            cardPay(product!!.price)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 4000) {
            data?.let {
                val dialog = ProcessingDialog()
                dialog.show(supportFragmentManager, "processing")
                api.payment(machine!!.mac, product!!.price, product!!.runningTime)
                        .observeOnMainThread()
                        .subscribe(
                                {
                                    dialog.dismiss()
                                    ResultDialog().show(supportFragmentManager, "result")
                                },
                                {
                                    dialog.dismiss()
                                    ErrorDialog().show(supportFragmentManager, "error")
                                }
                        )

            }
        } else {
            println(data?.extras)
            ErrorDialog().show(supportFragmentManager, "error")
        }
    }

    fun productSelect(machine: Machine) {
        this.machine = machine
        (fragments[1] as ProductSelectFragment).updateList(machine)
        pageChange(1)
    }

    fun paymentAgree(product: Product) {
        this.product = product
        (fragments[2] as SelectResultFragment).bindView(machine!! to product)
        pageChange(2)
    }

    fun pageChange(page: Int) {
        if (page < 0) {
            this.finish()
        } else {
            viewpager.currentItem = page
        }
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }

    fun cardPay(ammount: Int) {
        val APPPOS_API_KEY = "kr.infinix.hpay.appposw"

        val uri = "appposw://card-approval?" +
                "&authKey=${APPPOS_API_KEY}" +
                "&inputAmount=${ammount}" +
                "&needPrint=false" +
                "&showResult=false" +
                "&inputAmountEditable=false" +
                "&inputMonthEditable=false" +
                "&inputMemoEditable=false" +
                "&autoPayButtonClick=true"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addCategory(Intent.CATEGORY_BROWSABLE)
            setData(Uri.parse(uri))
        }

        startActivityForResult(intent, 4000)
    }
}

data class Bill(
        // 거래번호
        val approvalNo: String?,
        // 거래일자
        val approvalDate: Date?,
        // 사업자 등록번호
        val regNumber: String?,
        // 단말기 번호
        val tid: String?,
        // 카드번호
        val cardNumber: String?,
        // 최종 결제 금액
        val price: Long
)

fun Intent.payResult():Bill = Bill(
        this.getStringExtra("approvalNo"),
        this.getStringExtra("approvalDate").formatDate("YYYYMMDDHHmmss"),
        this.getStringExtra("regNumber"),
        this.getStringExtra("tid"),
        this.getStringExtra("cardNumber"),
        this.getLongExtra("price", 0L)

)