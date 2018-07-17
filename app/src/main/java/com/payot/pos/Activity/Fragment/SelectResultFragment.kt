package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.payot.pos.Data.Database.Machine
import com.payot.pos.Data.Database.Product
import com.payot.pos.R
import com.payot.pos.Utils.toLocaleString

class SelectResultFragment : Fragment() {

    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_select_result, container, false)

        return rootView
    }

    fun bindView(item: Pair<Machine, Product>) {
        rootView.findViewById<TextView>(R.id.txtMachineName).text = "${item.first.name} (${item.second.name})"
        rootView.findViewById<TextView>(R.id.txtPrice).text = "${item.second.price.toLocaleString()} 원"

        Glide.with(this)
                .load(R.drawable.ic_help)
                .into(rootView.findViewById(R.id.imgHelp))
    }
}