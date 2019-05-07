package com.limbo.kotlindex


import android.util.Log
import androidx.paging.PagedList
import retrofit2.Callback
import androidx.paging.PositionalDataSource
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.models.SearchResultsModel
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor

// network executor is only used for retry logic :(
// not required to minimally complete this task

// keep track of offset position and hardcode in page limit per pull!
class PokemonDataSource(private val pokeService: PokeApiHttpService) : PositionalDataSource<SearchResultModel>() {
    private val TAG = "PokeDataSource"
    // private val BASE_URL = "https://pokeapi.co/api/v2/"
    private val TOTAL_COUNT = 964 // currently 964 kinds of pokemon

    // mutable live data for networkState and initialLoading...

    // This data source is responsible for for loading data from a network request and storing paged list results here
    // controller is responsible for connecting the data in the viewmodel to the UI (View)

    // Q: How do I make network requests on a separate thread so my datasource class can have the data it needs?
    // A: networkExecutor class needs to be created!???

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<SearchResultModel>) {
        Log.d(TAG, ".loadRange called with position: ${params.startPosition} and loadSize: ${params.loadSize}")
        callback.onResult(loadRangeInternal(params.startPosition, params.loadSize))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<SearchResultModel>) {
        val position: Int = computeInitialLoadPosition(params, TOTAL_COUNT)
        val loadSize: Int = computeInitialLoadSize(params, position, TOTAL_COUNT)

        Log.d(TAG, ".loadInitial called with position: $position and loadSize: $loadSize")

        val options = HashMap<String, String>()
        options["offset"] = position.toString()
        options["limit"] = loadSize.toString()
        pokeService.obtainPokemonSearchResults(options).enqueue(object: Callback<SearchResultsModel> {
            override fun onFailure(call: Call<SearchResultsModel>, t: Throwable) {
                Log.d(TAG, ".onFailure called because of: ${t.message}")
            }

            override fun onResponse(call: Call<SearchResultsModel>, response: Response<SearchResultsModel>) {
                if(response.isSuccessful) {
                    Log.d(TAG, ".onResponse called with response: ${response.body()!!.results}")
                    callback.onResult(response.body()!!.results, position, loadSize)
                }
            }
        })
    }

    private fun loadRangeInternal(startPosition: Int, loadCount: Int): List<SearchResultModel> {
        //pokeService.
        Log.d(TAG, ".loadRangeInternal called with startPosition: $startPosition and loadCount: $loadCount")

        var resultsList: List<SearchResultModel> = ArrayList<SearchResultModel>()
        val options = HashMap<String, String>()
        options["offset"] = startPosition.toString()
        options["limit"] = loadCount.toString()
        pokeService.obtainPokemonSearchResults(options).enqueue(object: Callback<SearchResultsModel> {
            override fun onFailure(call: Call<SearchResultsModel>, t: Throwable) {
                Log.d(TAG, ".onFailure called because of: ${t.message}")
            }

            override fun onResponse(call: Call<SearchResultsModel>, response: Response<SearchResultsModel>) {
                if(response.isSuccessful) {
                    Log.d(TAG, ".onResponse called with response: ${response.body()}")

                    // how to return this async data????????????
                    resultsList = response.body()!!.results
                }
            }
        })

        return resultsList
    }
}