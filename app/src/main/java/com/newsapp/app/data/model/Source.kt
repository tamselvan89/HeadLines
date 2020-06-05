package com.newsapp.app.data.model

import com.google.gson.annotations.SerializedName

data class Source(

    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    var name: String

)