package com.example.entities

data class Categories (
    val name: String,
    val auth: String,
    val imageurl: String,
    val podcasts: List<String>
) {
    constructor() : this("","","", listOf())
}