package com.limbo.kotlindex

import com.limbo.kotlindex.models.FlavorTextEntriesModel
import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface PokeApiHttpService {
    @GET
    fun obtainPokemonSearchResultByUrlAsync(@Url url: String) : Deferred<PokemonModel>

    @GET("pokemon")
    fun obtainPokemonSearchResultsAsync(@QueryMap options: Map<String, String>) : Deferred<SearchResultsModel>

    @GET("pokemon-species/{name}")
    fun obtainPokemonFlavorTextsAsync(@Path("name") name: String) : Deferred<FlavorTextEntriesModel>

    @GET("pokemon/{name}")
    fun obtainPokemonByNameAsync(@Path("name") name: String) : Deferred<PokemonModel>
}