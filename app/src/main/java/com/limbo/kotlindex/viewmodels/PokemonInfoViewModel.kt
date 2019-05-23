package com.limbo.kotlindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.limbo.kotlindex.models.FlavorTextEntryModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class PokemonInfoViewModel(private val pokeRepo: PokeApiHttpRepository): ViewModel() {

    fun getFlavorText(pokemonName: String): LiveData<FlavorTextEntryModel> {
        return pokeRepo.obtainPokemonFlavorText(pokemonName)
    }
}