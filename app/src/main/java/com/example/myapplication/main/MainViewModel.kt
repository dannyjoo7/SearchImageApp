package com.example.myapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _todoEvent: MutableLiveData<String> = MutableLiveData()
    val todoEvent: LiveData<String> get() = _todoEvent

    fun updateSearchWord(word: String) {
        _todoEvent.value = word
    }
}