package com.example.ikeabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var items: List<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val submitButton = findViewById<Button>(R.id.submitBtn)
        val newName = findViewById<EditText>(R.id.nameEt)
        val newPrice = findViewById<EditText>(R.id.priceEt)
        val newUrl = findViewById<EditText>(R.id.urlEt)
        var itemCount = 0
        var id = 0

        // Lookup the RecyclerView in activity layout
        val itemsRv = findViewById<RecyclerView>(R.id.itemsRv)
        // Fetch the list of items
        items = ItemFetcher.getItems()

        // Create adapter passing in the list of items
        val adapter = ItemAdapter(items)
        // Attach the adapter to the RecyclerView to populate items
        itemsRv.adapter = adapter
        // Set layout manager to position the items
        itemsRv.layoutManager = LinearLayoutManager(this)

        submitButton.setOnClickListener { v ->
            // Get the user-entered information

            // Add the new item to the list
            ItemFetcher.addItem(newName.text.toString(),
                                newPrice.text.toString().toDouble(),
                                newUrl.text.toString(),
                                id)
            id += 1
            itemCount += 1
            // Update the RecyclerView
            adapter.notifyDataSetChanged()
            // Clear the old data
            newName.text.clear()
            newPrice.text.clear()
            newUrl.text.clear()

        }
    }
}