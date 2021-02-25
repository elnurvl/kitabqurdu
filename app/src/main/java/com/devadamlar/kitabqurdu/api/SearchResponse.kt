package com.devadamlar.kitabqurdu.api

import com.devadamlar.kitabqurdu.models.Book
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    val start: Int,
    @SerializedName("numFound")
    val numFound: Int,
    val docs: List<Book>
)