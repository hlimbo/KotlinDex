package com.limbo.kotlindex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.limbo.kotlindex.PokeApiHttpService
import com.limbo.kotlindex.PokeApiServiceFactory
import com.limbo.kotlindex.PokemonDataSourceFactory
import com.limbo.kotlindex.PokemonDataSourceFactory2
import com.limbo.kotlindex.models.SearchResultModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors

// will get used by a ViewModel
class PokeApiNetworkPagingRepository {
    private val TAG = "PokeNetworkPageRepo"
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val api: PokeApiHttpService by lazy {
        PokeApiServiceFactory.create(BASE_URL)
    }

    // old crummy way
    fun getPokemonList(pageSize: Int) : LiveData<PagedList<SearchResultModel>> {
        Log.d(TAG, ".getPokemonList($pageSize) called")
        val pokemonDataSourceFactory = PokemonDataSourceFactory(api)

        return LivePagedListBuilder(pokemonDataSourceFactory, pageSize).build()

//        val livePagedList = pokemonDataSourceFactory.toLiveData(
//            config = Config(
//                pageSize = pageSize,
//                enablePlaceholders = false,
//                initialLoadSizeHint = pageSize
//            ),
//            fetchExecutor =  NETWORK_IO
//        )

//        return livePagedList
    }

    fun getPokemonList2(pageSize: Int) : LiveData<PagedList<SearchResultModel>> {
        Log.d(TAG, ".getPokemonList2($pageSize) called")
        val pokemonDataSourceFactory2 = PokemonDataSourceFactory2(api)
        return LivePagedListBuilder(pokemonDataSourceFactory2, pageSize).build()
    }
}