package com.example.myapplication.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Item
import com.example.myapplication.provider.ContextProvider
import com.example.myapplication.provider.MainContextProviderImpl

class MainViewModel(
    private val contextProvider: ContextProvider
) : ViewModel() {
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

    fun loadSearchWord(): String {
        val sharedPreferences = contextProvider.getSharedPreferences()
        return sharedPreferences.getString("searchWord", "") ?: ""
    }

    fun saveSearchWord(word: String) {
        val sharedPreferences = contextProvider.getSharedPreferences()
        val editor = sharedPreferences.edit()
        editor.putString("searchWord", word)
        editor.apply()
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

class MainViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                MainContextProviderImpl(context)
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}