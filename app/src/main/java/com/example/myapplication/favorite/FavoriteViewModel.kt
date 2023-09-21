package com.example.myapplication.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.data.Repository
import com.example.myapplication.search.SearchViewModel

class FavoriteViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    private val _favorite = MutableLiveData<MutableList<Item>>()

    // 외부에서 변경할 수 없는 LiveData를 공개
    val favorite: LiveData<MutableList<Item>>
        get() = _favorite

    init {
        _favorite.value = repository.findFavoriteItems()
    }

    fun removeFavoriteItem(item: Item) {
        // 라이브 데이터 업데이트...
        val currentList = _favorite.value ?: mutableListOf()
        currentList.remove(item)
        _favorite.value = currentList
    }

    fun addFavoriteItem(item: Item) {
        // 라이브 데이터 업데이트...
        val currentList = _favorite.value ?: mutableListOf()
        currentList.add(item)
        _favorite.value = currentList
    }
}

class FavoriteViewModelFactory(
    private val repository: ItemRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}