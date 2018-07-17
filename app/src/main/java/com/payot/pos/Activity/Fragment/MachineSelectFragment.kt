package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.payot.pos.Activity.PaymentActivity
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerFragmentComponent
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.R
import javax.inject.Inject

class MachineSelectFragment : RecyclerViewFragment() {

    @Inject
    lateinit var dao: AppDatabaseDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerFragmentComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = Adapter()
    }

    private class ViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

        val txtMachineName: TextView
        val txtMachineInfo: TextView

        init {
            txtMachineName = parentView.findViewById(R.id.txtMachineNmae)
            txtMachineInfo = parentView.findViewById(R.id.txtMachineInfo)
        }
    }

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.listitem_machine, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int = dao.getMachines().size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val machine = dao.getMachines()[position]

            holder.run {
                txtMachineName.text = machine.name
                txtMachineInfo.text = machine.info
                itemView.setOnClickListener {
                    (activity as PaymentActivity).productSelect(machine)
                }
            }
        }

    }
}