package com.example.rmp.characters.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rmp.characters.domain.CharactersRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository


    private lateinit var charactersViewModel: CharactersViewModel



    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        charactersRepository = Mockito.mock(CharactersRepository::class.java)
        charactersViewModel = CharactersViewModel(charactersRepository)

    }

    @Test
    fun getCharactersListTest() {

        val characterList = arrayListOf(CharacterItems("id", "name", "species", "image"))
        runBlockingTest { given(charactersRepository.getCharactersList()).willReturn(characterList) }
        charactersViewModel.getCharactersList()
        val result = charactersViewModel.charactersList.value
        Assert.assertEquals(characterList, result)


    }
}
