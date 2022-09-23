package com.example.ikeabuddy

import android.util.Log

class ItemFetcher {
    companion object {
        val itemList = mutableListOf<Item>()

        fun getItems(): MutableList<Item>{
            return itemList
        }

        fun addItem(name:String, price:Double, url:String){
            val newItem = Item(name, price, url)
            itemList.add(newItem)
        }

        fun removeItem(index: Int){
            itemList.removeAt(index)
        }

        fun logItem(index:Int){
            val testingItem = itemList.get(index)
            Log.v("index", testingItem.name.toString())
        }

    }
}