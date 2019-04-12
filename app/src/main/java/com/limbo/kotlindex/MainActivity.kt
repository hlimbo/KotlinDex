package com.limbo.kotlindex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.limbo.kotlindex.viewmodels.SearchResultViewModel
import kotlinx.android.synthetic.main.search_results_recycler_view.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var searchResultViewModel: SearchResultViewModel
    private val searchResultAdapter = SearchResultAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results_recycler_view)

        search_recycler_view.layoutManager = LinearLayoutManager(this)
        search_recycler_view.adapter = searchResultAdapter
        search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))

        searchResultViewModel = ViewModelProviders.of(this).get(SearchResultViewModel::class.java)

        searchResultViewModel.getSearchResults().observe(this, Observer {
            Log.d(TAG, "the data $it")
            if(it != null) {
                for (result in it.results) {
                    searchResultViewModel.getSearchResult(result.url).observe(this, Observer { pokemonModel ->
                        if (pokemonModel != null) {
                            searchResultAdapter.insertTheData(pokemonModel)
                        }
                    })
                }
            }
        })
    }
}
