package com.example.myapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item

class MainViewModel : ViewModel() {
    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> get() = _searchWord

    private val _event: MutableLiveData<MainEventForFavorite> = MutableLiveData()
    val event: LiveData<MainEventForFavorite> get() = _event

    fun updateSearchWord(word: String) {
        _searchWord.value = word
    }

    fun addFavoriteItem(item: Item) {
        _event.value = MainEventForFavorite.AddFavoriteItem(item)
    }
}

sealed interface MainEventForFavorite {
    data class AddFavoriteItem(
        val item: Item
    ) : MainEventForFavorite
}