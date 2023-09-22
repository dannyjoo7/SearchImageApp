package com.example.myapplication.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong

class SearchViewModel(
    private val repository: ItemRepository,
) : ViewModel() {

    private val _search = MutableLiveData<MutableList<Item>>()

    val search: LiveData<MutableList<Item>>
        get() = _search

    init {
        _search.value = repository.findSearchItems()
    }

    fun searchImage(word: String) {
        viewModelScope.launch {
            // test
            val response = repository.searchImage(word, "recency")
            if (response.isSuccessful) {
                val itemList = response.body() ?: mutableListOf()
                val favoriteList = repository.findFavoriteItems()

                for (favorite in favoriteList) {
                    val foundItem = itemList.find { it.image_url == favorite.image_url }

                    if (foundItem != null) {
                        foundItem.isFavorite = true
                    }
                }
                _search.value = itemList
            } else {
                // null일 시 공백 리스트 생성
                _search.value = mutableListOf()
            }
        }
    }

    fun searchItem(word: String) {
        viewModelScope.launch {
            // test
            val list = mutableListOf<Item>()
            val responseImg = repository.searchImage(word, "recency")

            if (responseImg.isSuccessful) {
                val itemList = responseImg.body() ?: mutableListOf()
                val favoriteList = repository.findFavoriteItems()

                for (favorite in favoriteList) {
                    val foundItem = itemList.find { it.image_url == favorite.image_url }

                    if (foundItem != null) {
                        foundItem.isFavorite = true
                    }
                }
                list.addAll(itemList)
            } else {
                // null일 시 공백 리스트 생성
                _search.value = mutableListOf()
            }

            val responseVideo = repository.searchVideo(word, "recency")
            if (responseVideo.isSuccessful) {
                val itemList = responseVideo.body() ?: mutableListOf()
                val favoriteList = repository.findFavoriteItems()

                for (favorite in favoriteList) {
                    val foundItem = itemList.find { it.image_url == favorite.image_url }

                    if (foundItem != null) {
                        foundItem.isFavorite = true
                    }
                }
                list.addAll(itemList)
            } else {
                // null일 시 공백 리스트 생성
                _search.value = mutableListOf()
            }

            list.sortByDescending { it.datetime }
            _search.value = list
        }
    }

    fun addFavoriteItem(item: Item) {
        repository.addFavoriteItem(item)
    }

    fun updateItem(item: Item) {
        val curList = _search.value ?: return
        val curItem = curList.find { it.image_url == item.image_url } ?: return

        val position = curList.indexOf(curItem)
        curList[position] = item.copy(isFavorite = false)
        _search.value = curList
    }
}

class SearchViewModelFactory(
    private val repository: ItemRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}