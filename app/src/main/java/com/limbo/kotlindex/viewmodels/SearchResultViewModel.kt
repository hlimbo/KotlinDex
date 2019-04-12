package com.limbo.kotlindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class SearchResultViewModel: ViewModel() {
    private val pokeRepo: PokeApiHttpRepository by lazy {
        PokeApiHttpRepository()
    }

    fun getSearchResults(): LiveData<SearchResultsModel> {
        return pokeRepo.obtainPokemonSearchResults()
    }

    fun getSearchResult(url: String): LiveData<PokemonModel> {
        return pokeRepo.obtainPokemonSearchResultByUrl(url)
    }
}