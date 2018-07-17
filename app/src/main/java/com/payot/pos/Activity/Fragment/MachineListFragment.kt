package com.payot.pos.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.payot.pos.Activity.ProductSettingActivity
import com.payot.pos.App
import com.payot.pos.DI.Components.DaggerFragmentComponent
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import com.payot.pos.Data.Database.Machine
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MachineListFragment : Fragment() {

    @Inject
    lateinit var dao: AppDatabaseDAO

    lateinit var machinePublishSubject: PublishSubject<Machine>

    val recyclerView: RecyclerView by lazy {
        RecyclerView(activity).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = Adapter(dao)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerFragmentComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        machinePublishSubject = (activity as ProductSettingActivity).selectMachine

        return recyclerView
    }

    private inner class Adapter(dao: AppDatabaseDAO) : RecyclerView.Adapter<ViewHolder>() {
        val items = dao.getMachines()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = items[position]
            holder.textView.text = data.name

            holder.rootView.setOnClickListener {
                machinePublishSubject.onNext(data)
            }
        }

    }

    private class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        val textView: TextView

        init {
            textView = rootView.findViewById(android.R.id.text1)
        }
    }
}