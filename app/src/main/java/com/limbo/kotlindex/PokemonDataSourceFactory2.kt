package com.limbo.kotlindex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.limbo.kotlindex.models.SearchResultModel

class PokemonDataSourceFactory2(private val pokeApi: PokeApiHttpService) : DataSource.Factory<String, SearchResultModel>() {
    private val TAG = "PokeDataSrcFactory2"
    private val sourceLiveData = MutableLiveData<PokemonDataSource2>()
    override fun create(): DataSource<String, SearchResultModel> {
        val source = PokemonDataSource2(pokeApi)
        sourceLiveData.postValue(source)

        Log.d(TAG, ".create() called with source: $source")
        Log.d(TAG, ".create() called with source invalid? ${source.isInvalid}")

        return source
    }
}