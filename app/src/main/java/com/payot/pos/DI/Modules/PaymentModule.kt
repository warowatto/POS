package com.payot.pos.DI.Modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.payot.pos.Activity.Dialog.ErrorDialog
import com.payot.pos.Activity.Dialog.ProcessingDialog
import com.payot.pos.Activity.Dialog.ResultDialog
import com.payot.pos.Activity.PaymentActivity
import com.payot.pos.DI.PerActivity
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Product
import com.payot.pos.Utils.formatDate
import com.payot.pos.Utils.observeOnMainThread
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toSingle

@Module
class PaymentModule(val activity: PaymentActivity) {

    val APPPOS_API_KEY = "kr.infinix.hpay.appposw"

    @PerActivity
    @Provides
    fun pay(api: RestAPI, dao: AppDatabaseDAO): AppPOSPay = object : AppPOSPay {
        override fun pay(product: Product): Single<Boolean> {
            val uri = "appposw://card-approval?" +
                    "&authKey=${APPPOS_API_KEY}" +
                    "&inputAmount=${1004}" +
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

            val dialog = ProcessingDialog()
            activity.startActivityForResult(intent, 4000)

            return activity.payResult
                    .filter { return@filter it.requestCode == 4000 }
                    .map {
                        if (it.resultCode == Activity.RESULT_CANCELED) throw PaymentExecption("결제처리에 오류가 발생하였습니다")
                        println("데이터 수신 받음 ${it.intent.extras}")
                        return@map it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { dialog.show(activity.supportFragmentManager, "processing") }
                    .map {
                        return@map mapOf(
                                "approvalNo" to it.intent.getStringExtra("approvalNo"),
                                "approvalDate" to it.intent.getStringExtra("approvalDate").formatDate("YYYYMMDDHHmmss"),
                                "regNumber" to it.intent.getStringExtra("regNumber"),
                                "cardNumber" to it.intent.getStringExtra("cardNumber"),
                                "price" to it.intent.getLongExtra("sumVal", 0L)
                        )
                    }.flatMap {
                        val mac = dao.getMachinie(product.machineId).mac

                        return@flatMap api.payment(mac, product.price, product.runningTime).toObservable()
                                .observeOnMainThread()
                                .doOnNext { ResultDialog().show(activity.supportFragmentManager, "result") }
                                .doOnError { ErrorDialog().show(activity.supportFragmentManager, "error") }
                                .doOnComplete { dialog.dismiss() }
                    }.map { return@map true }.blockingFirst().toSingle()
        }
    }
}

class PaymentExecption(message: String) : Exception(message)

interface AppPOSPay {
    fun pay(product: Product): Single<Boolean>
}