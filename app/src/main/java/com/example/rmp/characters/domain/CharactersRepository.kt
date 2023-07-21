package com.example.rmp.characters.domain

import com.example.rmp.characters.presentation.Character.CharacterDetailsItems
import com.example.rmp.characters.presentation.Character.CharacterItems
interface CharactersRepository{
    suspend fun getCharactersList(): ArrayList<CharacterItems>
    suspend fun getCharacterDetails(characterId: String): CharacterDetailsItems?
}