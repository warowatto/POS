package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MachineListFragment : Fragment() {

    val recyclerView: RecyclerView by lazy {
        RecyclerView(activity).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = Adapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return recyclerView
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {
        val items = arrayOf("1번 세탁기", "2번 세탁기", "3번 세탁기", "4번 세탁기", "5번 세탁기", "1번 건조기", "2번 건조기", "3번 건조기")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = items[position]
            holder.textView.text = data
        }

    }

    private class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        lateinit var textView: TextView

        init {
            textView = rootView.findViewById(android.R.id.text1)
        }
    }
}