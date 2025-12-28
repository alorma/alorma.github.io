package com.alorma.playground.domain.model

data class Repository(
    val name: String,
    val fullName: String,
    val htmlUrl: String,
    val pagesUrl: String,
    val description: String?
)
