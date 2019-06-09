package com.limbo.kotlindex.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.limbo.kotlindex.PokemonDataSourceFactory
import com.limbo.kotlindex.R
import com.limbo.kotlindex.PokemonResultPagedListAdapter
import com.limbo.kotlindex.SearchResultRecyclerListAdapter
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.util.getViewModel
import com.limbo.kotlindex.viewmodels.PokemonResultPagingViewModel
import kotlinx.android.synthetic.main.search_results_recycler_view.*

class MainFragment : BaseFragment() {
    private val TAG = "MainFrag"

    private var isSearchQueryPresent = false
    private val theSearchResult = ArrayList<SearchResultModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_results_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // how to maintain scroll position when swapping between adapters?
        // swap out adapters ~ one that shows search results, and one that eventually shows all pokemon records as user scrolls down
        val pokemonAdapter = PokemonResultPagedListAdapter()
        val searchAdapter = SearchResultRecyclerListAdapter()
        search_recycler_view.layoutManager = LinearLayoutManager(activity)
        search_recycler_view.adapter = if(isSearchQueryPresent) searchAdapter else pokemonAdapter
        search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))

        if(search_recycler_view.adapter is SearchResultRecyclerListAdapter) {
            // re-find the previous query data made by the user if user decides to navigate back to this fragment
            searchAdapter.submitData(theSearchResult)
        }

        val model = getViewModel { PokemonResultPagingViewModel(pokeApiHttpRepository) }

        // Exact Query Match Feature
        // make a network call to obtain all 964 pokemon names and store into array
        var pokemonSearchResults = ArrayList<SearchResultModel>() // convert to hashmap where key is pokemonName and value is pokemon number
        val pokemonMap = HashMap<String, String>()
        model.searchResultsTotal().observe( this, Observer<SearchResultsModel> {
            Log.d(TAG, "searchResultsTotal: ${it.count}")
            model.getAllPokemonNames(it.count).observe(this, Observer<SearchResultsModel> { inner ->
                pokemonSearchResults = ArrayList(inner.results)
                for(pokemon in pokemonSearchResults) {
                    pokemonMap[pokemon.name] = pokemon.url
                }
            })
        })

        model.getPokemonPagedList().observe(this, Observer<PagedList<SearchResultModel>> {
            Log.d(TAG, "onChanged invoked on searchResults().observe()")
            // only gets called once
            pokemonAdapter.submitList(it)
        })

        mySearchView.setOnCloseListener {
            Log.d(TAG, "USER WILL CLOSE search view")
            isSearchQueryPresent = false
            if(search_recycler_view.adapter !is PokemonResultPagedListAdapter) {
                // swap back to pokemonAdapter
                pokemon_not_found_view.visibility = View.GONE
                search_recycler_view.visibility = View.VISIBLE
                search_recycler_view.adapter = pokemonAdapter
                search_recycler_view.addItemDecoration(
                    DividerItemDecoration(
                        search_recycler_view.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            false
        }

        mySearchView.setOnSearchClickListener {
            Log.d(TAG, "user has clicked on search button and query string is: ${mySearchView.query}")
            isSearchQueryPresent = true
        }

        // on Query submitted, locate exact pokemon name in list
        // if name not found in list, display Pokemon Name: "$pokemonName" not found in the backend... view
        // if name is found, display pokemon number, pokemon name, and icon ~ calling 2 endpoints to retrieve textual and image data
        // on clearing the Query, return to displaying paged list view of pokemon

        mySearchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit invoked with query search string: $query")
                theSearchResult.clear()

                // find pokemon by name in list
                val pokemonNames = pokemonSearchResults.map { result -> result.name }
                if(pokemonNames.contains(query)) {
                    Log.d(TAG, "onQueryTextSubmit invoked found pokemon: $query in the list")
                    // Things tried
                    // 1. tried to reuse model.getPokemonResults.observe() to clear and manually add in the data found <- failed due to pagedList not supporting add method
                    // 2. tried to create my on PagedList.Builder instance but requires executors
                    // 3. tried to use LivePageListBuilder and set the value of pagedList but is unable to do so since one has to initialize a pagedList first before setting it....

                    theSearchResult.add(SearchResultModel(query!!, pokemonMap[query]!!))
                    search_recycler_view.adapter = searchAdapter
                    search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))
                    searchAdapter.submitData(theSearchResult)

                } else {
                    Log.d(TAG, "onQueryTextSubmit pokemon NOT FOUND: $query in the list")
                    // display not found view by disabling/hiding recycler view
                    // enable visibility for not found view
                    isSearchQueryPresent = false
                    search_recycler_view.visibility = View.GONE
                    pokemon_not_found_view.visibility = View.VISIBLE
                }

                return false
            }

            // can use this to code suggestive search filter feature
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        Log.d(TAG,"pokeApiHttpRepository string: $pokeApiHttpRepository")
    }

}