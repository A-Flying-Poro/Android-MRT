package edu.singaporetech.mrtstationdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.singaporetech.mrtstationdb.databinding.ItemStationBinding
import edu.singaporetech.mrtstationdb.model.MrtStationModel

private val diffCallback = object : DiffUtil.ItemCallback<MrtStationModel>() {
    override fun areItemsTheSame(oldItem: MrtStationModel, newItem: MrtStationModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MrtStationModel,
        newItem: MrtStationModel
    ): Boolean = oldItem == newItem
}

class MrtStationAdapter() :
    ListAdapter<MrtStationModel, MrtStationAdapter.ViewHolder>(diffCallback),
    RecyclerViewClickListener {
    class ViewHolder(
        private val binding: ItemStationBinding,
        private val listener: RecyclerViewClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: MrtStationModel) {
            with(binding) {
                root.setOnClickListener(this@ViewHolder)
                model = item
                executePendingBindings()
            }
        }

        override fun onClick(view: View?) {
            listener.onItemClick(view ?: return, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun onItemClick(view: View, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
    }
}