package com.limbo.kotlindex

import android.net.Uri
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.models.SearchResultsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

// Does the data source get updated by the android framework in a separate thread from ui thread by default? YES
// See https://www.youtube.com/watch?v=BE5bsyGGLf4
// Android Jetpack: manage infinite lists with RecyclerView and Paging (Google I/O '18)
class PokemonDataSource2(private val pokeService: PokeApiHttpService) : PageKeyedDataSource<String, SearchResultModel>() {
    private val TAG = "PokeDataSource2"
    private val TOTAL_COUNT = 964 // TODO: get total number of pokemon records over the network to verify if dataset has been invalidated or not

    // called when page is initially loaded on the phone to view
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, SearchResultModel>
    ) {

        Log.d(TAG, ".loadInitial called with requested load size: ${params.requestedLoadSize} and placeholders enabled? ${params.placeholdersEnabled}")

        // let's test to see if we can make async calls here or will Android Framework disallow this behavior?
        // it does not sadly the documentation is misleading from the beginning as this is the exception to the rule :(
        val options = HashMap<String, String>()
        options["offset"] = "0"
        options["limit"] = "20"
        val request: Call<SearchResultsModel> = pokeService.obtainPokemonSearchResults(options)
        try {
            val response = request.execute()
            val pokemonSearchResults = response.body()?.results ?: emptyList()
            val nextPageUrl = response.body()?.nextPageUrl
            Log.d(TAG, ".loadInitial called will do callback.onResult method")
            callback.onResult(pokemonSearchResults, null, nextPageUrl)

        } catch(ioException: IOException) {
            // could retry http request here again!
            Log.d(TAG, ".loadInitial failed with ioException message: ${ioException.message}")
        }

    }



    // called when scrolling the page down
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, SearchResultModel>) {
        Log.d(TAG, ".loadAfter called with key: ${params.key} and requested load size${params.requestedLoadSize}")

        // STRING PARSE url to set offset and limit params
        val nextPageUrl = Uri.parse(params.key)
        if(nextPageUrl.queryParameterNames.isNotEmpty()) {
            val options = HashMap<String, String>()
            options["offset"] = nextPageUrl.getQueryParameter("offset")!!
            options["limit"] = nextPageUrl.getQueryParameter("limit")!!
            for(item in options) {
                Log.d(TAG, ".loadAfter: options[${item.key}] = ${item.value}")
            }

            // let's test to see if async call works here as well?
            pokeService.obtainPokemonSearchResults(options).enqueue(object: Callback<SearchResultsModel> {
                override fun onFailure(call: Call<SearchResultsModel>, t: Throwable) {
                    Log.d(TAG, ".onFailure called")
                }

                override fun onResponse(call: Call<SearchResultsModel>, response: Response<SearchResultsModel>) {
                    if(response.isSuccessful) {
                        Log.d(TAG, ".onResponse successful")
                        callback.onResult(response.body()?.results ?: emptyList(), response.body()?.nextPageUrl)
                    } else {
                        Log.d(TAG, ".onResponse not successful")
                        Log.d(TAG, ".onResponse failure message: ${response.errorBody()}")
                    }
                }
            })
        }

    }

    // called when scrolling the page up
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, SearchResultModel>) {
        Log.d(TAG, ".loadBefore called with key: ${params.key} and requested load size${params.requestedLoadSize}")

    }
}