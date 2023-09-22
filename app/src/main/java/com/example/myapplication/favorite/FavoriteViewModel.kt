package com.example.myapplication.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.provider.ContextProvider
import com.example.myapplication.provider.MainContextProviderImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteViewModel(
    private val repository: ItemRepository,
    private val contextProvider: ContextProvider
) : ViewModel() {

    private val _favorite = MutableLiveData<MutableList<Item>>()

    // 외부에서 변경할 수 없는 LiveData를 공개
    val favorite: LiveData<MutableList<Item>>
        get() = _favorite

    init {
        _favorite.value = repository.findFavoriteItems()
        _favorite.value = _favorite.value?.let { list ->
            list.apply {
                addAll(loadDeviceData())
            } ?: mutableListOf()
        }
    }

    fun removeFavoriteItem(item: Item) {
        // 라이브 데이터 업데이트...
        val currentList = _favorite.value ?: mutableListOf()
        currentList.remove(item)
        _favorite.value = currentList
        saveDeviceData(currentList)
    }

    fun addFavoriteItem(item: Item) {
        // 라이브 데이터 업데이트...
        val currentList = _favorite.value ?: mutableListOf()

        // 중복 북마크 방지
        if (currentList.contains(item)) return
        currentList.add(item)
        _favorite.value = currentList
        saveDeviceData(currentList)
    }

    fun saveDeviceData(list: MutableList<Item>) {
        val gson = Gson()
        val listAsJson = gson.toJson(list)

        val sharedPreferences = contextProvider.getSharedPreferences()
        val editor = sharedPreferences.edit()
        editor.putString("favorite", listAsJson)
        editor.apply()
    }


    private fun loadDeviceData(): MutableList<Item> {
        val sharedPreferences = contextProvider.getSharedPreferences()
        val list = sharedPreferences.getString("favorite", "") ?: ""

        val gson = Gson()
        val itemType = object : TypeToken<MutableList<Item>>() {}.type

        return gson.fromJson(list, itemType) ?: mutableListOf()
    }
}

class FavoriteViewModelFactory(
    private val repository: ItemRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            repository,
            MainContextProviderImpl(context)
        ) as T
    }
}