package com.example.grocerycalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
/**
class SavedFilesAdapter(
    private val files: List<File>,
    //what does this next line do
    private val onFileAction: (fileName: String, action: String) -> Unit
): RecyclerView.Adapter<SavedFilesAdapter.FileViewHolder>() {
    override fun onCreteViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_file, parent, false)
        return FileViewHolder(view)
    }

    override fun OnBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileName = filesList[position]
        holder.fileNameTextView.text = fileName

        holder.itemView.setOnClickListener {
            onFileAction(fileName, "load")
        }

        holder.itemView.setOnLongClickListener {
            onFileAction(fileName, "delete")
            true
        }
        override fun getItemCount(): Int = filesList.size

        class FileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
        }
    }
}
        **/