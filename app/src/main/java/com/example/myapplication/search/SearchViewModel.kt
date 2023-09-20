package com.example.myapplication.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.data.Repository

class SearchViewModel : ViewModel() {
    private val repository:Repository = ItemRepository()

    private val _search = MutableLiveData<MutableList<Item>>()

    // 외부에서 변경할 수 없는 LiveData를 공개
    private val search: LiveData<MutableList<Item>>
        get() = _search

    init {
        _search.value = repository.findSearchItems()
    }

    fun getItemList(): LiveData<MutableList<Item>> {
        return search
    }
    fun addFavoriteItem(item: Item) {
        repository.addFavoriteItem(item)
    }
}