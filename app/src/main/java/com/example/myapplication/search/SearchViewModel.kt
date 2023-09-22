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

    private var word = ""
    private var page = 0

    private val _search = MutableLiveData<MutableList<Item>>()

    val search: LiveData<MutableList<Item>>
        get() = _search

    init {
        _search.value = repository.findSearchItems()
    }

    fun searchItem(word: String) {

        // 값 초기화...
        this.word = word
        this.page = 0

        viewModelScope.launch {
            val list = mutableListOf<Item>()

            // Image
            val responseImg = repository.searchImage(word, "recency", ++page)

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

            // Video
            val responseVideo = repository.searchVideo(word, "recency", page)
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

            Log.d("test", "초기값 : ${list.size}")
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            val list = mutableListOf<Item>()

            // Image
            val responseImg = repository.searchImage(word, "recency", ++page)

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

            // Video
            val responseVideo = repository.searchVideo(word, "recency", page)
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

            // 새로운 페이지 아이템 추가...
            val curList = _search.value ?: mutableListOf()
            curList.addAll(list)
            _search.value = curList

            Log.d("test", "페이지 로딩 후 값 : ${curList.size}")
        }
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