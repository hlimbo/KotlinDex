package com.limbo.kotlindex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.limbo.kotlindex.PokeApiHttpService
import com.limbo.kotlindex.PokeApiServiceFactory
import com.limbo.kotlindex.models.FlavorTextEntryModel
import com.limbo.kotlindex.models.LanguageModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.*
import retrofit2.HttpException

class PokeApiHttpRepository {
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    private val TAG = "PokeAPIRepo"

    private val pokeApiService: PokeApiHttpService by lazy {
        PokeApiServiceFactory.create(BASE_URL)
    }
    private val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    fun obtainPokemonSearchResults(options: Map<String, String>): LiveData<SearchResultsModel> {
//        val data: MutableLiveData<SearchResultsModel> = MutableLiveData()

//        pokeApiService.obtainPokemonSearchResults(options).enqueue(object: Callback<SearchResultsModel> {
//            override fun onFailure(call: Call<SearchResultsModel>, t: Throwable) {
//                Log.d(TAG, ".obtainPokemonSearchResults onFailure method invoked")
//            }
//
//            override fun onResponse(call: Call<SearchResultsModel>, response: Response<SearchResultsModel>) {
//                Log.d(TAG, ".onResponse called")
//                if(response.isSuccessful) {
//                    data.value = response.body()
//                } else {
//                    Log.d(TAG,".onResponse called when obtainPokemonSearchResults() is called is not successful")
//                    Log.d(TAG, "Error: ${response.errorBody()?.string()}")
//                }
//            }
//        })

//        return data

        // coroutine way
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
//        val data: MutableLiveData<PokemonModel> = MutableLiveData()
//
//        pokeApiService.obtainPokemonSearchResultByUrl(url).enqueue(object: Callback<PokemonModel> {
//            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
//                Log.d(TAG, ".obtainPokemonSearchResultByUrl onFailure method called")
//            }
//
//            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
//                if(response.isSuccessful) {
//                    data.value = response.body()
//                }
//            }
//        })
//
//        return data

        // coroutine way
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
}