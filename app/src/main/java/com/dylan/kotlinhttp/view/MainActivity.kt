package com.dylan.kotlinhttp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dylan.kotlinhttp.R
import com.dylan.kotlinhttp.Utils.Utils
import com.dylan.kotlinhttp.adapter.HttpTypeAdapter
import com.dylan.kotlinhttp.interfase.HttpTypeItemClickListener
import com.dylan.kotlinhttp.ui.bindView

class MainActivity : AppCompatActivity(), HttpTypeItemClickListener {
    private val mHttpTypes: Array<String> by lazy {
        resources.getStringArray(R.array.http_types)
    }

    private val mHttpActions: Array<String> by lazy {
        resources.getStringArray(R.array.http_actions)
    }

    private val mHttpTypeAdapter: HttpTypeAdapter by lazy {
        HttpTypeAdapter(this@MainActivity,
                this@MainActivity, mHttpTypes)
    }

    private val mRecyclerView: RecyclerView by bindView(R.id.recycler_view)

    override fun onItemClick(view: View, position: Int) {
        Utils.showToast(this@MainActivity, mHttpActions[position])
        Utils.startHttpRequest(this@MainActivity, mHttpActions[position])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView.adapter = mHttpTypeAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity,
                DividerItemDecoration.VERTICAL))
    }
}
