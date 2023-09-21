package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    val documents: MutableList<Item>?
)