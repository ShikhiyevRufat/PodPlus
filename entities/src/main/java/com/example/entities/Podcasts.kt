package com.example.entities

data class Podcasts (
    val id : String,
    val name: String,
    val imageurl: String,
    val audio: String
){
    constructor() : this("","","","")
}