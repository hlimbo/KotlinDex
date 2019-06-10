package com.limbo.kotlindex.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class PokemonResultPagingViewModel(private val repository: PokeApiHttpRepository): ViewModel() {
    // class will hold the paged results of the pokemon data here!
    private val TAG = "SearchResultPageVM"

    // the data is coming from the PokeApiNetworkPagingRepository instance
    fun getPokemonPagedList(): LiveData<PagedList<SearchResultModel>> {
        Log.d(TAG, "searchResults() called")
        return repository.obtainPokemonPagedList(20)
    }

    fun searchResultsTotal(): LiveData<SearchResultsModel> {
        return repository.obtainPokemonSearchResults(HashMap())
    }

    fun getAllPokemonNames(pokemonCount: Int): LiveData<SearchResultsModel> {
        val options = HashMap<String, String>()
        options["offset"] = "0"
        options["limit"] = pokemonCount.toString()
        return repository.obtainPokemonSearchResults(options)
    }

    // make another function that combines pokemon names with pokemon image resource paths
}