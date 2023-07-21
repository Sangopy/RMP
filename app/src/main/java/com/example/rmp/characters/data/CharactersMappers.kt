package com.example.rmp.characters.data

import com.example.CharacterDetailsQuery
import com.example.CharactersListQuery
import com.example.rmp.characters.presentation.Character.CharacterDetailsItems
import com.example.rmp.characters.presentation.Character.CharacterItems

fun CharactersListQuery.Result.toCharacters(): CharacterItems {
    return CharacterItems(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image

    )
}

fun CharacterDetailsQuery.Character.toDetailedCharacter(): CharacterDetailsItems {

    return CharacterDetailsItems(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        created = created,
        image = image,
    )
}