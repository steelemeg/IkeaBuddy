package com.example.ikeabuddy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.ikeabuddy.MainActivity.ClickListener


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
        var index = -1

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

        // Long press logic based on https://medium.com/@harivigneshjayapalan/android-recyclerview-implementing-single-item-click-and-long-press-part-ii-b43ef8cb6ad8
        itemsRv.addOnItemTouchListener(
            RecyclerTouchListener(this,
                itemsRv, object : ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        // On single click, take no action
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        ItemFetcher.logItem(position)
                        ItemFetcher.removeItem(position)
                        adapter.notifyItemRemoved(position)
                        //ItemFetcher.removeItem(position)
                        itemCount --
                    }
                })
        )
        submitButton.setOnClickListener { v ->
            // Get the user-entered information.
            // Add the new item to the list
            ItemFetcher.addItem(newName.text.toString(),
                                newPrice.text.toString().toDouble(),
                                newUrl.text.toString())
            itemCount ++
            // Update the RecyclerView
            // Based on https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
            adapter.notifyItemChanged(itemCount)
            // Clear the old data
            newName.text.clear()
            newPrice.text.clear()
            newUrl.text.clear()

        }
    }

    interface ClickListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }
}

internal class RecyclerTouchListener(
    context: Context?,
    recycleView: RecyclerView,
    private val clicklistener: ClickListener?
) :
    OnItemTouchListener {
    // Based on https://medium.com/@harivigneshjayapalan/android-recyclerview-implementing-single-item-click-and-long-press-part-ii-b43ef8cb6ad8
    private val gestureDetector: GestureDetector
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
            clicklistener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    init {
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recycleView.findChildViewUnder(e.x, e.y)
                if (child != null && clicklistener != null) {
                    clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                }
            }
        })
    }
}

