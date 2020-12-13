package com.pominova.surfmemesapp.model

class Meme(
    var id: String,
    var title: String,
    var description: String,
    var isFavorite: Boolean,
    var createdDate: Int,
    var photoUrl: String
)