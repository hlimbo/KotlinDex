package com.limbo.kotlindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class SearchResultViewModel(private val repo: PokeApiHttpRepository): ViewModel() {
    fun getSearchResults(options: Map<String, String>): LiveData<SearchResultsModel> {
        return repo.obtainPokemonSearchResults(options)
    }

    fun getSearchResult(url: String): LiveData<PokemonModel> {
        return repo.obtainPokemonSearchResultByUrl(url)
    }
}