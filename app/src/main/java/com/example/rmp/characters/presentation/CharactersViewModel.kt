package com.example.rmp.characters.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmp.characters.data.RmpCharactersRepository
import com.example.rmp.characters.domain.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {


    val characterDetails = MutableLiveData<CharacterDetailsItems>()
    val charactersList = MutableLiveData<List<CharacterItems>>()


    fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO){
            charactersList.postValue(charactersRepository.getCharactersList())
        }
    }

    fun getCharacterDetailed(characterID: String) {
        viewModelScope.launch(Dispatchers.IO){
            characterDetails.postValue(charactersRepository.getCharacterDetails(characterID))
        }
    }
}