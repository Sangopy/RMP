package com.example.rmp.characters.presentation.Character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmp.characters.domain.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    var characters = ArrayList<CharacterItems>()
    val characterDetails = MutableLiveData<CharacterDetailsItems>()
    val charactersList = MutableLiveData<List<CharacterItems>>()

    var genderFilter = mutableSetOf<String>()
    var statusFilter = mutableSetOf<String>()
    val filterResults = MutableLiveData(listOf<CharacterItems?>())
    var gender: Set<String?> = setOf()
    var status: Set<String?> = setOf()

    val isFilterApplied: Boolean
        get() = genderFilter.isNotEmpty() || statusFilter.isNotEmpty()

    val filteredResultsNumber = MutableLiveData(0)

    fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                characters = charactersRepository.getCharactersList()
                charactersList.postValue(characters)
            }
            status = characters.map { it.status }.toSet()
            gender = characters.map { it.gender }.toSet()
        }

    }

    fun getCharacterDetailed(characterID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            characterDetails.postValue(charactersRepository.getCharacterDetails(characterID))
        }
    }

    fun getGenderList(): Set<String?> {
        runBlocking {
            gender = charactersRepository.getCharactersList().map { it.gender }.toSet()
        }
        return gender
    }

    fun getStatusList(): Set<String?> {
        runBlocking {
            status = charactersRepository.getCharactersList().map { it.status }.toSet()
        }
        return status
    }


    init {
        getCharactersList()
    }

    fun updateGenderFilter(gender: String, checked: Boolean) {
        if (checked) {
            genderFilter.add(gender)
        } else {
            genderFilter.remove(gender)
        }
        updateFilterResults()
    }

    fun updateStatusFilter(status: String, checked: Boolean) {
        if (checked) {
            statusFilter.add(status)
        } else {
            statusFilter.remove(status)
        }
        updateFilterResults()
    }

    fun updateFilterResults() {
        viewModelScope.launch(Dispatchers.IO) {
            if (genderFilter.isEmpty() && statusFilter.isEmpty()) {
                filterResults.postValue(ArrayList())
                filteredResultsNumber.postValue(0)
            } else {
                val genderFilter =
                    this@CharactersViewModel.genderFilter.toSet()

                val filterClosure: (String?, String?) -> Boolean = { gender, status ->

                    val genderResult = when {
                        genderFilter.isEmpty() -> true
                        genderFilter.contains(gender) -> true
                        else -> false
                    }

                    val statusResult = when {
                        statusFilter.isEmpty() -> true
                        statusFilter.contains(status) -> true
                        else -> false
                    }

                    // Combine results
                    genderResult && statusResult
                }
                val filterCharacters = characters.filter { filterClosure(it.gender, it.status) }

                filterResults.postValue(filterCharacters)

                filteredResultsNumber.postValue(filterCharacters.size)

            }
        }
    }

    fun reset() {
        genderFilter.clear()
        statusFilter.clear()
        updateFilterResults()
    }


}