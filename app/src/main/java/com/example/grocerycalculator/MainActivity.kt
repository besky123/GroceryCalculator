package com.example.grocerycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckboxAdapter
    private val itemList = mutableListOf<CheckboxItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CheckboxAdapter(itemList)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editText)
        val addButton = findViewById<Button>(R.id.addButton)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)


        addButton.setOnClickListener {
            val itemText = editText.text.toString()
            val itemPrice = priceEditText.text.toString().toDoubleOrNull()?: 0.0

            if (itemText.isNotBlank()){
                addItemToList(itemText, itemPrice)
                editText.text.clear()
                priceEditText.text.clear()
            }
        }
    }

    private fun addItemToList(itemText: String, itemPrice: Double){
        val newItem = CheckboxItem(itemText, false, itemPrice)
        adapter.addItem(newItem)
        Log.d("MainActivity", "Item added:$itemText with price: $itemPrice")
    }
}