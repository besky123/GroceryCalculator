package com.example.grocerycalculator

import android.content.Context
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

fun saveListToFile(context: Context, fileName: String, itemList: List<CheckboxItem>) {
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

fun loadListFromFile(context: Context, fileName: String): List<CheckboxItem> {
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
