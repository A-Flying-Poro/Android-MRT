package edu.singaporetech.mrtstationdb.adapter

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClick(view: View, position: Int)
}