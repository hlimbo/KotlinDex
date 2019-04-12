package com.limbo.kotlindex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.limbo.kotlindex.PokeApiHttpService
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeApiHttpRepository {
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    private val TAG = "PokeAPIRepo"
    private val retroFitInstance: Retrofit by lazy {
        // By Default, Gson omits all fields that are null during serialization ~ source of all bugs if values to be returned in a field are not present....
        // https://google.github.io/gson/apidocs/com/google/gson/GsonBuilder.html#serializeNulls--
        val gson: Gson = GsonBuilder().setVersion(1.0).create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val pokeApiService: PokeApiHttpService by lazy {
        retroFitInstance.create(PokeApiHttpService::class.java)
    }

    fun obtainPokemonSearchResults(): LiveData<SearchResultsModel> {
        val data: MutableLiveData<SearchResultsModel> = MutableLiveData()

        pokeApiService.obtainPokemonSearchResults().enqueue(object: Callback<SearchResultsModel> {
            override fun onFailure(call: Call<SearchResultsModel>, t: Throwable) {
                Log.d(TAG, ".obtainPokemonSearchResults onFailure method invoked")
            }

            override fun onResponse(call: Call<SearchResultsModel>, response: Response<SearchResultsModel>) {
                Log.d(TAG, ".onResponse called")
                if(response.isSuccessful) {
                    data.value = response.body()
                } else {
                    Log.d(TAG,".onResponse called when obtainPokemonSearchResults() is called is not successful")
                    Log.d(TAG, "Error: ${response.errorBody()?.string()}")
                }
            }
        })

        return data
    }

    fun obtainPokemonSearchResultByUrl(url: String): LiveData<PokemonModel> {
        val data: MutableLiveData<PokemonModel> = MutableLiveData()

        pokeApiService.obtainPokemonSearchResultByUrl(url).enqueue(object: Callback<PokemonModel> {
            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                Log.d(TAG, ".obtainPokemonSearchResultByUrl onFailure method called")
            }

            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                if(response.isSuccessful) {
                    data.value = response.body()
                }
            }
        })

        return data
    }
}