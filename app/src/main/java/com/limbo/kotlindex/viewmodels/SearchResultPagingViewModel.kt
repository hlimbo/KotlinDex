package com.limbo.kotlindex.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository

class SearchResultPagingViewModel(private val repository: PokeApiHttpRepository): ViewModel() {
    // class will hold the paged results of the pokemon data here!
    private val TAG = "SearchResultPageVM"

    // the data is coming from the PokeApiNetworkPagingRepository instance
    fun searchResults(): LiveData<PagedList<SearchResultModel>> {
        val results = repository.obtainPokemonPagedList(20)
        Log.d(TAG, ".searchResults() with with observers?: ${results.hasObservers()}")
        Log.d(TAG, ".searchResults() with with active observers?: ${results.hasActiveObservers()}")
        Log.d(TAG, ".searchResults() with with value?: ${results.value?.get(0)?.name}")
        return results
    }

    // make another function that combines pokemon names with pokemon image resource paths
}