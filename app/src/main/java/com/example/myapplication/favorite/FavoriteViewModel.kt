package com.example.myapplication.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository

class FavoriteViewModel : ViewModel() {
    private val repository = ItemRepository()

    fun getItemList(): LiveData<List<Item>> {
        return repository.getFavoriteItemList()
    }

    fun deleteItem(item: Item) {
        repository.removeFavoriteItem(item)
    }

    fun addFavoriteItem(item: Item) {
        repository.addFavoriteItem(item)
    }

    fun removeFavoriteItem(item: Item) {
        repository.removeFavoriteItem(item)
    }
}