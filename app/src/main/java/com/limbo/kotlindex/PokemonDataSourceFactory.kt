package com.limbo.kotlindex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.limbo.kotlindex.models.SearchResultModel
import java.util.concurrent.Executor

// Int is the key because we want to keep track of the page offset as the poke api
// supports offset query parameter
class PokemonDataSourceFactory(private val pokeApi: PokeApiHttpService) : DataSource.Factory<Int, SearchResultModel>() {
    val TAG = "PokeDataSrcFactory"
    val sourceLiveData = MutableLiveData<PokemonDataSource>()
    override fun create(): DataSource<Int, SearchResultModel> {
        val source = PokemonDataSource(pokeApi)
        sourceLiveData.postValue(source)

        Log.d(TAG, ".create() called with source: $source")
        Log.d(TAG, ".create() called with source invalid? ${source.isInvalid}")

        return source
    }
}