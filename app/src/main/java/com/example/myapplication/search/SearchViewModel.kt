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

    // 외부에서 변경할 수 없는 LiveData를 공개
    private val search: LiveData<MutableList<Item>>
        get() = _search

    fun searchImage() {
        viewModelScope.launch {
            val response = repository.searchImage("카리나", "recency")
            if (response.isSuccessful) {
                val itemList = response.body()
                _search.value = itemList!!
            } else {
                // 오류 처리를 수행하거나 필요한 방식으로 처리하세요.
            }
        }
    }


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