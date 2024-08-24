package com.example.grocerycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckboxAdapter
    private val itemList = mutableListOf<CheckboxItem>()
    private lateinit var totalTextView: TextView
    private lateinit var taxTextView: TextView
    private val taxRate = 0.13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)



        adapter = CheckboxAdapter(itemList) {
            calculateTotalandTax()
        }
        recyclerView.adapter = adapter

        totalTextView = findViewById(R.id.totalTextView)
        taxTextView = findViewById(R.id.taxTextView)

        val editText = findViewById<EditText>(R.id.editText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val addButton = findViewById<Button>(R.id.addButton)


        addButton.setOnClickListener {
            val itemText = editText.text.toString()
            val itemPrice = priceEditText.text.toString().toDoubleOrNull()?: 0.0

            if (itemText.isNotBlank()){
                addItemToList(itemText, itemPrice)
                editText.text.clear()
                priceEditText.text.clear()
            }
        }

        calculateTotalandTax()
    }

    private fun addItemToList(itemText: String, itemPrice: Double){
        val newItem = CheckboxItem(itemText, false, itemPrice)
        adapter.addItem(newItem)
        Log.d("MainActivity", "Item added:$itemText with price: $itemPrice")

        calculateTotalandTax()
    }

    private fun calculateTotalandTax(){
        val total = itemList.sumOf { it.price }
        val tax = total * taxRate

        totalTextView.text = "Total: $%.2f".format(total)
        taxTextView.text = "Tax: $%.2f".format(tax)
    }
}