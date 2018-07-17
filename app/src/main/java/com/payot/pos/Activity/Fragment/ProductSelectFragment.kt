package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.payot.pos.Activity.PaymentActivity
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerFragmentComponent
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Machine
import com.payot.pos.R
import com.payot.pos.Utils.toLocaleString
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ProductSelectFragment : RecyclerViewFragment() {

    @Inject
    lateinit var dao: AppDatabaseDAO

    var machineId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerFragmentComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = Adapter()
    }

    // 리스트 아이템
    private class ViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
        val txtProductName: TextView
        val txtProductPrice: TextView
        val txtProductRunningTime: TextView

        init {
            txtProductName = parentView.findViewById(R.id.txtProductName)
            txtProductPrice = parentView.findViewById(R.id.txtProductPrice)
            txtProductRunningTime = parentView.findViewById(R.id.txtProductServiceTime)
        }
    }

    fun updateList(machine: Machine) {
        machineId = machine.id!!
        recyclerView.adapter.notifyDataSetChanged()
    }

    // 리스트 어뎁터
    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.listitem_product, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int = getItems().size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val product = getItems()[position]

            holder.run {
                txtProductName.text = product.name
                txtProductPrice.text = "${product.price.toLocaleString()} 원"
                txtProductRunningTime.text = "${(product.runningTime / 60).toLocaleString()} 분"
                itemView.setOnClickListener {
                    (activity as PaymentActivity).paymentAgree(product)
                }
            }
        }

        fun getItems() = dao.getProducts(machineId)

    }


}