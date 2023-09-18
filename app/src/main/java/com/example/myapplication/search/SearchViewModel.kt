package com.example.myapplication.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository

class SearchViewModel : ViewModel() {
    private val repository = ItemRepository()

    fun getItemList(): LiveData<List<Item>> {
        return repository.getSearchItemList()
    }

    fun deleteItem(item: Item) {
        repository.removeFavoriteItem(item)
    }

    fun addFavoriteItem(item: Item) {
        repository.addFavoriteItem(item)
    }
}