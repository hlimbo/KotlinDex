package com.limbo.kotlindex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.repository.PokeApiHttpService

class PokemonDataSourceFactory(private val pokeApi: PokeApiHttpService) : DataSource.Factory<String, SearchResultModel>() {
    private val TAG = "PokeDataSrcFactory2"
    private val sourceLiveData = MutableLiveData<PokemonDataSource>()
    override fun create(): DataSource<String, SearchResultModel> {
        val source = PokemonDataSource(pokeApi)
        sourceLiveData.postValue(source)

        Log.d(TAG, ".create() called with source: $source")
        Log.d(TAG, ".create() called with source invalid? ${source.isInvalid}")

        return source
    }
}