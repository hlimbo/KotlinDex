package com.limbo.kotlindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class SearchResultViewModel: ViewModel() {
    private val pokeRepo: PokeApiHttpRepository by lazy {
        PokeApiHttpRepository()
    }

    fun getSearchResults(options: Map<String, String>): LiveData<SearchResultsModel> {
        return pokeRepo.obtainPokemonSearchResults(options)
    }

    fun getSearchResult(url: String): LiveData<PokemonModel> {
        return pokeRepo.obtainPokemonSearchResultByUrl(url)
    }
}