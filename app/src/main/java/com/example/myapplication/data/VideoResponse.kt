package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    val documents: MutableList<VideoItem>?
)

