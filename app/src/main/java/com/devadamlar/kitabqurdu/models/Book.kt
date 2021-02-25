package com.devadamlar.kitabqurdu.models

data class Book(
    val key: String,
    val title: String,
    val authorName: List<String>?,
    val authorKey: List<String>?,
    val coverI: Int,
    val firstPublishYear: Int,
    val editionCount: Int,
    val publisher: List<String>?
)