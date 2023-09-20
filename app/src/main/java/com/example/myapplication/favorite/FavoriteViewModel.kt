package com.example.myapplication.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository

class FavoriteViewModel : ViewModel() {
    private val repository = ItemRepository()

    private val _favorite = MutableLiveData<MutableList<Item>>()

    // 외부에서 변경할 수 없는 LiveData를 공개
    private val favorite: LiveData<MutableList<Item>>
        get() = _favorite

    init {
        _favorite.value = repository.findFavoriteItems()
    }

    fun getItemList(): LiveData<MutableList<Item>> {
        return favorite
    }

    fun removeFavoriteItem(item: Item) {
        // 라이브 데이터 업데이트...
        val currentList = _favorite.value ?: mutableListOf()
        currentList.remove(item)
        _favorite.value = currentList

        repository.removeFavoriteItem(item)
    }
}