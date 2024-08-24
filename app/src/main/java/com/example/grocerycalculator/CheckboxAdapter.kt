package com.example.grocerycalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class CheckboxAdapter(
    private val itemList: MutableList<CheckboxItem>,
    private val onItemChanged: () -> Unit):
    RecyclerView.Adapter<CheckboxAdapter.CheckboxViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkbox, parent, false)
        return CheckboxViewHolder(view)
    }
    override fun onBindViewHolder(holder: CheckboxViewHolder, position: Int) {
        val item = itemList[position]
        holder.checkBox.isChecked = item.isChecked
        holder.itemEditText.setText(item.text)
        holder.priceEditText.setText(item.price.toString())

        Log.d("CheckboxAdapter", "Binding item: ${item.text} with price: ${item.price}")

        holder.itemEditText.setOnFocusChangeListener {_, hasFocus ->
            if (!hasFocus) {
                item.text = holder.itemEditText.text.toString()
                onItemChanged()
            }
        }

        holder.priceEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newPrice = holder.priceEditText.text.toString().toDoubleOrNull()?: item.price
                item.price = newPrice
                onItemChanged()
            }
        }
        holder.checkBox.setOnCheckedChangeListener {_, isChecked ->
            item.isChecked = isChecked
            onItemChanged()
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun addItem(item: CheckboxItem){
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
        onItemChanged()
    }

    class CheckboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val itemEditText: EditText = itemView.findViewById(R.id.itemEditText)
        val priceEditText: TextView = itemView.findViewById(R.id.priceEditText)
    }

}
