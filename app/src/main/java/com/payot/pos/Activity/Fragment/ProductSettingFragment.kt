package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.payot.pos.Activity.ProductSettingActivity
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerFragmentComponent
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Machine
import com.payot.pos.Data.Database.Product
import com.payot.pos.R
import com.payot.pos.Utils.toLocaleString
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ProductSettingFragment : Fragment() {

    @Inject
    lateinit var dao: AppDatabaseDAO

    lateinit var selectedMachine: PublishSubject<Machine>

    lateinit var rootView: View
    lateinit var txtMachineName: TextView
    lateinit var txtMachineMac: TextView
    lateinit var txtMachineType: TextView
    lateinit var txtMachineInfo: TextView

    lateinit var editProductName: EditText
    lateinit var editProductPrice: EditText
    lateinit var editProductRunningTime: EditText

    lateinit var btnProductAdd: Button

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerFragmentComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        rootView = inflater.inflate(R.layout.fragment_product_setting, container, false)

        txtMachineName = rootView.findViewById(R.id.txtMachineName)
        txtMachineMac = rootView.findViewById(R.id.txtMachineMac)
        txtMachineType = rootView.findViewById(R.id.txtMachineType)
        txtMachineInfo = rootView.findViewById(R.id.txtMachineInfo)

        editProductName = rootView.findViewById(R.id.editProductName)
        editProductPrice = rootView.findViewById(R.id.editProductPrice)
        editProductRunningTime = rootView.findViewById(R.id.editProductRunningTime)

        btnProductAdd = rootView.findViewById(R.id.btnProductAdd)

        selectedMachine = (activity as ProductSettingActivity).selectMachine
        recyclerView = (rootView.findViewById(R.id.recyclerView) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = Adapter()

        var machine: Machine? = null

        Observable.merge(listOf(Observable.just(dao.getMachines()[0]), selectedMachine))
                // 장치 정보 바인딩
                .doOnNext { bindMachine(it); machine = it }
                .map { return@map dao.getProducts(it.id!!) }
                .subscribe(
                        {
                            (recyclerView.adapter as Adapter).setData(it)
                        },
                        {
                            it.printStackTrace()
                        }
                )
                .addTo((activity as ProductSettingActivity).dispose)

        initView()

        RxView.clicks(btnProductAdd)
                .map {
                    return@map Product(null, machine!!.id!!, editProductName.text.toString(), editProductPrice.text.toString().toInt(), editProductRunningTime.text.toString().toInt())
                }
                .doOnNext { dao.setProduct(it) }
                .subscribe { (recyclerView.adapter as Adapter).setData(dao.getProducts(machine!!.id!!)) }
                .addTo((activity as ProductSettingActivity).dispose)
    }

    private fun initView() {
        val productName = RxTextView.textChanges(editProductName).map { return@map it.isNotEmpty() }
        val productPrice = RxTextView.textChanges(editProductPrice).map { if (it.isNotEmpty()) return@map it.toString().toInt() else return@map 0 }.map { return@map it >= 0 }

        Observable.combineLatest(productName, productPrice, BiFunction { t1: Boolean, t2: Boolean -> return@BiFunction t1 && t2 })
                .subscribe { btnProductAdd.isEnabled = it }.addTo((activity as ProductSettingActivity).dispose)
    }

    fun bindMachine(machine: Machine) {
        txtMachineName.text = machine.name
        txtMachineMac.text = machine.mac
        txtMachineType.text = machine.type
        txtMachineInfo.text = machine.info
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {
        var items: List<Product> = listOf()

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.txtName.text = item.name
            holder.txtPrice.text = "${item.price.toLocaleString()}원"
            holder.txtRuntime.text = "${item.runningTime}초 동작"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.listitem_product, parent, false)

            return ViewHolder(view)
        }

        fun setData(products: List<Product>) {
            this.items = products
            notifyDataSetChanged()
        }

    }

    private class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val txtName: TextView
        val txtPrice: TextView
        val txtRuntime: TextView

        init {
            txtName = rootView.findViewById(R.id.txtProductName)
            txtPrice = rootView.findViewById(R.id.txtProductPrice)
            txtRuntime = rootView.findViewById(R.id.txtProductServiceTime)
        }
    }
}