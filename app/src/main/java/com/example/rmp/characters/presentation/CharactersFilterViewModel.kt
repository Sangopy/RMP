package com.example.rmp.characters.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmp.characters.domain.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CharactersFilterViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    private var charactersList = ArrayList<CharacterItems>()
    var genderFilter = mutableSetOf<String>()
    var spaciesFilter = mutableSetOf<String>()
    var gender: Set<String> = setOf()
    val spacies: Set<String> = setOf()

    val filteredResultsNumber = MutableLiveData(0)

    var requestCanceled: Boolean = false
    val isFilterApplied: Boolean
        get() = genderFilter.isNotEmpty() || spaciesFilter.isNotEmpty()


    init {
        runBlocking {
            charactersList = charactersRepository.getCharactersList()
        }

        val charactersSpecies = charactersList.map { it.species }
        val charactersGenders = charactersList.map { it.gender }
    }

    fun updateGenderFilter(gender: String, checked: Boolean) {
        if (checked) {
            genderFilter.add(gender)
        } else {
            genderFilter.remove(gender)
        }
        updateFilterResults()
    }

    fun updateSpaciesFilter(spacies: String, checked: Boolean) {
        if (checked) {
            spaciesFilter.add(spacies)
        } else {
            spaciesFilter.remove(spacies)
        }
        updateFilterResults()
    }

    fun updateFilterResults() {
        viewModelScope.launch(Dispatchers.IO) {
            if (genderFilter.isEmpty() && spaciesFilter.isEmpty()) {
                filteredResultsNumber.postValue(0)
            } else {
                val gender = gender.toSet()
                val genderFilter =
                    this@CharactersFilterViewModel.genderFilter.toSet()

                val filterClosure: (List<String>, String?) -> Boolean = { genderList, spacies ->

                    val genderResult = when {
                        genderFilter.isEmpty() && (genderList.intersect(
                            gender
                        )
                            .isNotEmpty() || genderList.isEmpty()) -> true
                                (genderList.intersect(gender) == gender || genderList.isEmpty()) -> true
                        genderList.intersect(genderFilter).isNotEmpty() -> true
                        else -> false
                    }

                    val spaciesResult = when {
                        spaciesFilter.isEmpty() -> true
                        spaciesFilter.contains(spacies) -> true
                        else -> false
                    }

                    // Combine results
                    genderResult && spaciesResult
                }


            }
        }
    }


}
