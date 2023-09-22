package com.example.myapplication.data

import java.util.Date


data class Item(
    val title: String,
    val display_sitename: String,
    val image_url: String,
    val datetime: Date,
    var isFavorite: Boolean,
)

data class VideoItem(
    val title: String,
    val url: String,
    val datetime: Date,
    val thumbnail: String,
    var isFavorite: Boolean,
)

fun VideoItem.toItem(): Item {
    return Item(
        title = "title",
        display_sitename = title,
        image_url = thumbnail,
        datetime = datetime,
        isFavorite = isFavorite,
    )
}