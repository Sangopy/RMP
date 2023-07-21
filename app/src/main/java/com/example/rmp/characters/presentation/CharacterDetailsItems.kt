package com.example.rmp.characters.presentation

import java.io.Serializable

data class CharacterDetailsItems (
    val id: String?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val created: String?,
    val image: String?,
) : Serializable