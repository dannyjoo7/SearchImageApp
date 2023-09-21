package com.example.myapplication.search

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
    private val idGenerate: AtomicLong
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
            val response = repository.searchImage(word, "recency", idGenerate)
            if (response.isSuccessful) {
                val itemList = response.body()
                _search.value = itemList!!
            } else {
                // null일 시 공백 리스트 생성
                _search.value = mutableListOf()
            }
        }
    }

    fun addFavoriteItem(item: Item) {
        repository.addFavoriteItem(item)
    }
}

class SearchViewModelFactory(
    private val repository: ItemRepository
) : ViewModelProvider.Factory {

    private val idGenerate = AtomicLong(1L)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository, idGenerate) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}