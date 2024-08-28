package com.example.grocerycalculator

import com.example.grocerycalculator.saveListToFile
import com.example.grocerycalculator.loadListFromFile
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import java.io.File
import android.widget.Toast
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckboxAdapter
    private val itemList = mutableListOf<CheckboxItem>()
    private lateinit var totalTextView: TextView
    private lateinit var taxTextView: TextView
    private val taxRate = 0.13

    private lateinit var manageFilesLauncher: ActivityResultLauncher<Intent>

    private val REQUEST_CODE = 1
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
        val saveButton = findViewById<Button>(R.id.saveButton)
        //val loadButton = findViewById<Button>(R.id.loadButton)
        val manageFilesButton = findViewById<Button>(R.id.manageFilesButton)

        /**
        val newFileButton = findViewById<Button>(R.id.newFileButton)
        val loadFileButton = findViewById<Button>(R.id.loadFileButton)
        val deleteFileButton = findViewById<Button>(R.id.deleteFileButton)
        **/

        addButton.setOnClickListener {
            val itemText = editText.text.toString()
            val itemPrice = priceEditText.text.toString().toDoubleOrNull()?: 0.0

            if (itemText.isNotBlank()){
                addItemToList(itemText, itemPrice)
                editText.text.clear()
                priceEditText.text.clear()
            }
        }

        saveButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter file Name")

            //User input
            val input = EditText(this)
            builder.setView(input)

            //Buttons
            builder.setPositiveButton("Save") { dialog, _ ->
                val fileName = input.text.toString()
                if (fileName.isNotBlank()) {
                    val formattedFileName = "$fileName.dat"
                    saveListToFile(this, formattedFileName, itemList)
                } else {
                    Toast.makeText(this, "File name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancel") { dialog, _-> dialog.cancel() }
                builder.show()

            }
/**
        loadButton.setOnClickListener {
            val loadedList = loadListFromFile(this, "shopping_list.dat")
            itemList.clear()
            itemList.addAll(loadedList)
            adapter.notifyDataSetChanged()
            calculateTotalandTax()
        }
        **/
        manageFilesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val fileName = data?.getStringExtra("fileName")
                val action = data?.getStringExtra("action")

                when(action){
                    "load" -> {
                        if(fileName!=null){
                            val loadedList = loadListFromFile(this, fileName)
                            itemList.clear()
                            itemList.addAll(loadedList)
                            adapter.notifyDataSetChanged()
                            calculateTotalandTax()
                        }
                    }
                    "new" -> {
                        itemList.clear()
                        adapter.notifyDataSetChanged()
                        calculateTotalandTax()
                        Toast.makeText(this,"New List Created", Toast.LENGTH_SHORT).show()
                    }
                    "delete" ->{
                        if(fileName!=null) deleteF(fileName)
                    }
                }
            }
        }

        manageFilesButton.setOnClickListener {
            val intent = Intent(this, SavedFilesActivity::class.java)
            manageFilesLauncher.launch(intent)
        }
}
    private fun saveListToFile(context: Context, fileName: String, itemList: List<CheckboxItem>) {
        try {
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(itemList)
            objectOutputStream.close()
            fileOutputStream.close()
            Toast.makeText(context, "List saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving list", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadListFromFile(context: Context, fileName: String): List<CheckboxItem> {
        return try {
            val fileInputStream = context.openFileInput(fileName)
            val objectInputStream = ObjectInputStream(fileInputStream)
            @Suppress("UNCHECKED_CAST")
            val loadedList = objectInputStream.readObject() as List<CheckboxItem>
            objectInputStream.close()
            fileInputStream.close()
            Toast.makeText(context, "List loaded successfully", Toast.LENGTH_SHORT).show()
            loadedList
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error loading list", Toast.LENGTH_SHORT).show()
            emptyList()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun deleteF(fileName: String) {
        val file = File(filesDir, fileName)
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "File $fileName deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to delete file", Toast.LENGTH_SHORT).show()
        }
    }


    private fun calculateTotalandTax() {
        val total = itemList.sumOf { it.price }
        val tax = total * taxRate
        val ttotal = tax + total
        totalTextView.text = "Total: $%.2f".format(ttotal)
        taxTextView.text = "Tax: $%.2f".format(tax)
    }
    private fun addItemToList(itemText: String, itemPrice: Double){
        val newItem = CheckboxItem(itemText, false, itemPrice)
        adapter.addItem(newItem)
        Log.d("MainActivity", "Item added:$itemText with price: $itemPrice")
        calculateTotalandTax()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val fileName = data?.getStringExtra("fileName")
            val action = data?.getStringExtra("action")

            if(fileName != null && action != null){
                when (action) {
                    "load" -> loadListFromFile(this, fileName)
                    "delete" -> deleteF(fileName)
                }
            }
        }
    }
}

