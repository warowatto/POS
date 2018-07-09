package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.payot.pos.R

class ProductSettingFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product_setting, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = Adapter()
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int = 1

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.txtName.text = "일반세탁"
//            holder.txtPrice.text = "2000원"
//            holder.txtRuntime.text = "1800초 동작"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.listitem_product, parent, false)

            return ViewHolder(view)
        }

    }

    private class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        lateinit var txtName:TextView
        lateinit var txtPrice:TextView
        lateinit var txtRuntime:TextView

    }
}