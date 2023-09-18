package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ItemRepository {
    private val searchItemList: MutableLiveData<List<Item>> = MutableLiveData(data.searchData)
    private val favoriteItemList: MutableLiveData<List<Item>> = MutableLiveData(data.favoriteData)

    fun getSearchItemList(): LiveData<List<Item>> {
        return searchItemList
    }

    fun getFavoriteItemList(): LiveData<List<Item>> {
        return favoriteItemList
    }

    fun removeFavoriteItem(item: Item) {
        data.removeFavoriteItem(item)
        updateFavoriteItem()
    }

    fun addFavoriteItem(item: Item) {
        data.addFavoriteItem(item)
        updateFavoriteItem()
    }

    private fun updateSearchItem() {
        searchItemList.value = data.searchData
    }

    private fun updateFavoriteItem() {
        favoriteItemList.value = data.favoriteData
    }

    private fun updateAllList() {
        updateSearchItem()
        updateFavoriteItem()
    }
}