package com.example.myapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> get() = _searchWord

    fun updateSearchWord(word: String) {
        _searchWord.value = word
    }
}