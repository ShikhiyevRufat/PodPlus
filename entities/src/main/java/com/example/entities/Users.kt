package com.example.entities

data class Users (
    var userId: String,
    var name: String,
    var email: String,
){
    constructor() : this("","","")
}