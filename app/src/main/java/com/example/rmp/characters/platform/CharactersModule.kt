package com.example.rmp.platform.ui

import com.example.rmp.characters.data.RmpCharactersRepository
import com.example.rmp.characters.domain.CharactersRepository
import com.example.rmp.characters.presentation.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val charactersModule: Module = module {
    single<CharactersRepository> {
        RmpCharactersRepository()
    }

        viewModel { CharactersViewModel(get()) }



}