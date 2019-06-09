package com.limbo.kotlindex

import android.net.Uri
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.repository.PokeApiHttpService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

// Does the data source get updated by the android framework in a separate thread from ui thread by default? YES
// See https://www.youtube.com/watch?v=BE5bsyGGLf4
// Android Jetpack: manage infinite lists with RecyclerView and Paging (Google I/O '18)
class PokemonDataSource(private val pokeService: PokeApiHttpService) : PageKeyedDataSource<String, SearchResultModel>() {
    private val TAG = "PokeDataSource2"
    private var totalPokemonCount = 964

    private val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    // called when page is initially loaded on the phone to view
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, SearchResultModel>
    ) {

        Log.d(TAG, ".loadInitial called with requested load size: ${params.requestedLoadSize} and placeholders enabled? ${params.placeholdersEnabled}")
        val options = HashMap<String, String>()
        options["offset"] = "0"
        options["limit"] = "20"
        coroutineScope.launch {
            try {
                val response = pokeService.obtainPokemonSearchResultsAsync(options).await()
                totalPokemonCount = response.count
                callback.onResult(response.results, null, response.nextPageUrl)
            } catch(e: HttpException) {
                Log.d(TAG, ".loadInitial failed with exception message: ${e.message}")
                Log.d(TAG, ".loadInitial error body: ${e.response().errorBody()}")
            } catch(e: Throwable) {
                Log.d(TAG, ".loadInitial unexpected error ${e.message}")
            }
        }
    }



    // called when scrolling the page down
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, SearchResultModel>) {
        Log.d(TAG, ".loadAfter called with key: ${params.key} and requested load size${params.requestedLoadSize}")
        // parse url to set offset and limit params
        val nextPageUrl = Uri.parse(params.key)
        val options = HashMap<String, String>()
        if(nextPageUrl.queryParameterNames.isNotEmpty()) {
            options["offset"] = nextPageUrl.getQueryParameter("offset")!!
            options["limit"] = nextPageUrl.getQueryParameter("limit")!!
        }

        if(options.isEmpty()) {
            Log.d(TAG, ".loadAfter... options hashmap is empty (there is no next page url to navigate to")
        } else {
            coroutineScope.launch {
                try {
                    val response = pokeService.obtainPokemonSearchResultsAsync(options).await()
                    callback.onResult(response.results, response.nextPageUrl)
                } catch(e: HttpException) {
                    Log.d(TAG, ".loadAfter caught HttpException: ${e.response().errorBody()}")
                } catch(e: Throwable) {
                    Log.d(TAG, ".loadAfter unexpected error: ${e.message}")
                }
            }
        }


    }

    // called when scrolling the page up // only if data is invalidated or if scrollling should start from bottom to top
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, SearchResultModel>) {
        Log.d(TAG, ".loadBefore called with key: ${params.key} and requested load size${params.requestedLoadSize}")
    }
}