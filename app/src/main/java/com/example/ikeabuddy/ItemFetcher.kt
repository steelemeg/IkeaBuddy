package com.example.ikeabuddy

class ItemFetcher {
    companion object {
        val itemList = mutableListOf<Item>()

        fun getItems(): MutableList<Item>{
            return itemList
        }

        fun addItem(name:String, price:Double, url:String, id:Int){
            val newItem = Item(name, price, url, id)
            itemList.add(newItem)
        }

    }
}