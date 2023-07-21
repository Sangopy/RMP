package com.example.rmp.characters.data

import com.apollographql.apollo3.ApolloClient
import com.example.CharacterDetailsQuery
import com.example.CharactersListQuery
import com.example.rmp.characters.domain.CharactersRepository
import com.example.rmp.characters.presentation.Character.CharacterDetailsItems
import com.example.rmp.characters.presentation.Character.CharacterItems

open class RmpCharactersRepository: CharactersRepository {

    private val client =
        ApolloClient.Builder().serverUrl("https://rickandmortyapi.com/graphql").build()

    override suspend fun getCharactersList(): ArrayList<CharacterItems> {
        val charactersList = ArrayList<CharacterItems>()

        client.query(CharactersListQuery())
            .execute().data?.characters?.results?.forEach { it?.toCharacters()
                ?.let { itemCharacter -> charactersList.add(itemCharacter) } }

        return charactersList
    }


    override suspend fun getCharacterDetails(characterId: String): CharacterDetailsItems? {
        return client.query(CharacterDetailsQuery(characterId)).execute().data?.character?.toDetailedCharacter()
    }
}