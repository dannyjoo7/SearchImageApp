package com.example.myapplication.data

import java.util.Date


data class Item(
    val id: Long? = -1,
    val title: String,
    val display_sitename: String,
    val image_url: String,
    val datetime: Date,
    var isFavorite: Boolean,
)

data class FavoriteItem(
    val id: Long? = -1,
    val title: String,
    val display_sitename: String,
    val image_url: String,
    val datetime: Date,
    var isFavorite: Boolean,
)