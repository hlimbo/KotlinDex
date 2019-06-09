package com.limbo.kotlindex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.limbo.kotlindex.util.PokeApiServiceFactory
import com.limbo.kotlindex.PokemonDataSourceFactory
import com.limbo.kotlindex.models.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import javax.inject.Inject

class PokeApiHttpRepository @Inject constructor(private val pokeApiService: PokeApiHttpService, private val coroutineScope: CoroutineScope) {
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    private val TAG = "PokeAPIRepo"

    // alternative to using Dagger2 Dependency Injection Framework ~ introduce DIRECT dependencies composed within the class itself
    // instead of passing it through as constructor params
//    private val pokeApiService: PokeApiHttpService by lazy {
//        PokeApiServiceFactory.create(BASE_URL)
//    }
//    private val coroutineScope: CoroutineScope by lazy {
//        CoroutineScope(Dispatchers.IO)
//    }

    fun obtainPokemonSearchResults(options: Map<String, String>): LiveData<SearchResultsModel> {
        val data: MutableLiveData<SearchResultsModel> = MutableLiveData()

        coroutineScope.launch {
            try {
                val response = pokeApiService.obtainPokemonSearchResultsAsync(options).await()
                data.postValue(response)
            } catch(e: HttpException) {
                Log.d(TAG, ".obtainPokemonSearchResults caught an http exception: ${e.message} with error response: ${e.response().errorBody()}")
                Log.d(TAG, "error code: ${e.code()}")
            } catch(e: Throwable) {
                Log.d(TAG, ".obtainPokemonSearchResults caught a throwable exception: ${e.message}")
            }
        }

        return data
    }

    fun obtainPokemonSearchResultByUrl(url: String): LiveData<PokemonModel> {
        val data: MutableLiveData<PokemonModel> = MutableLiveData()
        coroutineScope.launch {
            try {
                val response = pokeApiService.obtainPokemonSearchResultByUrlAsync(url).await()
                data.postValue(response)
            } catch(e: HttpException) {
                Log.d(TAG, ".obtainPokemonSearchResultByUrl caught an http exception: ${e.message} with error response: ${e.response().errorBody()}")
                Log.d(TAG, "error code: ${e.code()}")
            } catch(e: Throwable) {
                Log.d(TAG, ".obtainPokemonSearchResultByUrl caught a throwable exception: ${e.message}")
            }
        }

        return data
    }

    fun obtainPokemonFlavorText(pokemonName: String) : LiveData<FlavorTextEntryModel> {
        val data: MutableLiveData<FlavorTextEntryModel> = MutableLiveData()
        coroutineScope.launch {
            try {
                val response = pokeApiService.obtainPokemonFlavorTextsAsync(pokemonName).await()

                // locate the correct flavor text entry to use (English language only)
                var isEnglishLanguageSupported = false
                for(flavorTextEntry in response.flavorTextEntries) {
                    if(flavorTextEntry.language.name == "en") {
                        data.postValue(flavorTextEntry)
                        isEnglishLanguageSupported = true
                    }
                }

                if(!isEnglishLanguageSupported) {
                    data.postValue(FlavorTextEntryModel("Sorry...current api does not support English language for this KotlinDex Entry", LanguageModel("N/A")))
                }

            } catch(e: HttpException) {
                Log.d(TAG, ".obtainPokemonFlavorText caught an http exception: ${e.message} with error response: ${e.response().errorBody()}")
                Log.d(TAG, "error code: ${e.code()}")
            } catch(e: Throwable) {
                Log.d(TAG, ".obtainPokemonFlavorText caught a throwable exception: ${e.message}")
            }
        }

        return data
    }

    fun obtainPokemonBasicInfo(pokemonName: String) : LiveData<PokemonModel> {
        val data: MutableLiveData<PokemonModel> = MutableLiveData()

        coroutineScope.launch {
            try {
                val response = pokeApiService.obtainPokemonByNameAsync(pokemonName).await()
//                Log.d(TAG, ".obtainPokemonBasicInfo: ${response.types.first()}")
//                Log.d(TAG, ".obtainPokemonBasicInfo: ${response.types.last()}")
                data.postValue(response)
            } catch(e: HttpException) {
                Log.d(TAG, ".obtainPokemonBasicInfo caught an http exception: ${e.message} with error response: ${e.response().errorBody()}")
                Log.d(TAG, "error code: ${e.code()}")
            } catch(e: Throwable) {
                Log.d(TAG, ".obtainPokemonBasicInfo caught a throwable exception: ${e.message}")
            }
        }

        return data
    }

    fun obtainPokemonPagedList(pageSize: Int) : LiveData<PagedList<SearchResultModel>> {
        //Log.d(TAG, ".getPokemonList2($pageSize) called")
        val pokemonDataSourceFactory2 = PokemonDataSourceFactory(pokeApiService)
        return LivePagedListBuilder(pokemonDataSourceFactory2, pageSize).build()
    }
}