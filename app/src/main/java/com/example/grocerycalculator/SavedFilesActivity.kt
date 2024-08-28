package com.example.grocerycalculator

import android.app.AlertDialog
import android.content.DialogInterface
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Button
import android.widget.Toast
import java.io.File

class SavedFilesActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_files)

        val listView = findViewById<ListView>(R.id.savedFilesListView)

        //Get list of files
        val fileDir = filesDir
        val files = fileDir.listFiles()
        val fileNames = files?.map{it.name}?: emptyList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)
        listView.adapter = adapter

        listView.setOnItemClickListener {_,_,position,_->
            val fileName = fileNames[position]
            val resultIntent = Intent()
            resultIntent.putExtra("fileName", fileName)
            resultIntent.putExtra("action", "load")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        //Long press Delete List Listener
        listView.setOnItemLongClickListener {_, _, position, _ ->
            val fileName = fileNames[position]
            AlertDialog.Builder(this)
                .setTitle("Delete File")
                .setMessage("Are you sure you want to delete $fileName?")
                .setPositiveButton("Yes") { dialog, _ ->
                    deleteF(fileName)
                    dialog.dismiss()
                    //Update List
                    val updatedFileNames = fileDir.listFiles()?.map {it.name} ?: emptyList()
                    adapter.clear()
                    adapter.addAll(updatedFileNames)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "$fileName delete", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        val newListButton = findViewById<Button>(R.id.newFileButton)
        newListButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("action", "new")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
    private fun deleteF(fileName: String) {
        val file = File(filesDir, fileName)
        if (file.exists()&& file.delete()) {
            Toast.makeText(this,"File $fileName deleted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Failed to delete File", Toast.LENGTH_SHORT).show()
        }
    }
}

