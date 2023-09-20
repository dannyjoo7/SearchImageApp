package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    val documents: MutableList<Item>?
)