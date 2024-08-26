package com.example.grocerycalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.File

class SavedFilesActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_files)

        val listView = findViewById<ListView>(R.id.savedFilesListView)

        //Get list of files
        val fileNames = fileList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)
        listView.adapter = adapter

        listView.setOnItemClickListener {_,_,position,_->
            val fileName = fileNames[position]
            val resultIntent = Intent()
            resultIntent.putExtra("fileName", fileName)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}

