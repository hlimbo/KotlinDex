package com.limbo.kotlindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.repository.PokeApiNetworkPagingRepository
import com.limbo.kotlindex.viewmodels.SearchResultPagingViewModel
import com.limbo.kotlindex.viewmodels.SearchResultViewModel
import kotlinx.android.synthetic.main.search_results_recycler_view.*

// implement pagination - DONE
// TODO: load sprite image of pokemon
// TODO: save data requested over the network on a local db on phone
// TODO: save image url paths on a local db
// TODO: implement search bar (exact bar search)
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var model: SearchResultPagingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results_recycler_view)

        Log.d(TAG, ".onCreate called")

        model = getViewModel()

        val adapter = SearchResultPagedListAdapter()
        search_recycler_view.layoutManager = LinearLayoutManager(this)
        search_recycler_view.adapter = adapter
        search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))

        model.searchResults().observe(this, Observer<PagedList<SearchResultModel>> {
            Log.d(TAG, ".onChanged called with it here: $it")

            // update ui with list obtained from this live data
            adapter.submitList(it)
        })
    }

    private fun getViewModel(): SearchResultPagingViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = PokeApiNetworkPagingRepository()

                @Suppress("UNCHECKED_CAST")
                return SearchResultPagingViewModel(repo) as T
            }
        })[SearchResultPagingViewModel::class.java]
    }

    // OLD IMPLEMENTATION

//    private lateinit var searchResultViewModel: SearchResultViewModel
//    private val searchResultAdapter = SearchResultAdapter(ArrayList())
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.search_results_recycler_view)
//
//        search_recycler_view.layoutManager = LinearLayoutManager(this)
//        search_recycler_view.adapter = searchResultAdapter
//        search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))
//
//        searchResultViewModel = ViewModelProviders.of(this).get(SearchResultViewModel::class.java)
//
//        // query parameters of offset and limit
//
//        // the problem: Textual information about the pokemon's name is stored in /pokemon whereas pokemon's picture is stored in /pokemon/{pokemonNumber} which is stored in a url that needs to be loaded
//        // data to build 1 page is located in 2 different endpoints (how to consolidate these pieces of information to create the page?)
//
//        // 1. for efficiency (limit number of API calls made to obtain pokemon information)... store information already fetched from the network in the phone's local db (mysqllite)
//        // 2.   - how do we know if the cached data held in the db is stale? (compare the number of total pokemon returned against the cached value to determine whether or not its good!)
//
//        // 2 things
//        // 1. store the response data retrieved in Room db
//        // 2. viewModel will request data from Room db only (room db httpRepo glue class will determine which data retrieval object to use (the local db or the network)
//
//
//        // To implement pagination, let's try using
//        // * PageKeyedDataSource -> current api includes next/previous link with each page load
//        // * PostionalDataSource
//
//        val options = HashMap<String, String>()
//        options["offset"] = "20"
//        options["limit"] = "20"
//        searchResultViewModel.getSearchResults(options).observe(this, Observer {
//            Log.d(TAG, "the data $it")
//            if(it != null) {
//                for (result in it.results) {
//                    searchResultViewModel.getSearchResult(result.url).observe(this, Observer { pokemonModel ->
//                        if (pokemonModel != null) {
//                            searchResultAdapter.insertTheData(pokemonModel)
//                        }
//                    })
//                }
//            }
//        })
//    }
}
