package com.example.grocerycalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class CheckboxAdapter(private val itemList: MutableList<CheckboxItem>):
    RecyclerView.Adapter<CheckboxAdapter.CheckboxViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkbox, parent, false)
        return CheckboxViewHolder(view)
    }
    override fun onBindViewHolder(holder: CheckboxViewHolder, position: Int) {
        val item = itemList[position]
        holder.checkBox.text = item.text
        holder.checkBox.isChecked = item.isChecked

        holder.checkBox.setOnCheckedChangeListener {_, isChecked ->
            item.isChecked = isChecked
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun addItem(item: CheckboxItem){
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    class CheckboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

}
