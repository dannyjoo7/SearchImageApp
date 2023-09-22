package com.example.myapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item

class MainViewModel : ViewModel() {
    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> get() = _searchWord

    private val _favoriteEvent: MutableLiveData<MainEventForFavorite> = MutableLiveData()
    val favoriteEvent: LiveData<MainEventForFavorite> get() = _favoriteEvent

    fun updateSearchWord(word: String) {
        _searchWord.value = word
    }

    fun addFavoriteItem(item: Item) {
        _favoriteEvent.value = MainEventForFavorite.AddFavoriteItem(item)
    }

    fun removeFavoriteItem(item: Item) {
        _favoriteEvent.value = MainEventForFavorite.RemoveFavoriteItem(item)
    }
}

sealed interface MainEventForFavorite {
    data class AddFavoriteItem(
        val item: Item
    ) : MainEventForFavorite

    data class RemoveFavoriteItem(
        val item: Item
    ) : MainEventForFavorite
}