package com.limbo.kotlindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.limbo.kotlindex.models.FlavorTextEntryModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class PokemonInfoViewModel(private val pokeRepo: PokeApiHttpRepository) : ViewModel() {

    fun getFlavorText(pokemonName: String): LiveData<FlavorTextEntryModel> {
        return pokeRepo.obtainPokemonFlavorText(pokemonName)
    }

    fun getPokemonBasicInfo(pokemonName: String): LiveData<PokemonModel> {
        return pokeRepo.obtainPokemonBasicInfo(pokemonName)
    }
}