package com.example.myapplication.data

import java.time.LocalDateTime

data class Item(
    val title: String,
    val display_sitename: String,
    val image_url: String,
    val datetime: LocalDateTime,
)
