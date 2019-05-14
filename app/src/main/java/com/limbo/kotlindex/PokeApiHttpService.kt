package com.limbo.kotlindex

import com.limbo.kotlindex.models.PokemonModel
import com.limbo.kotlindex.models.SearchResultsModel
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface PokeApiHttpService {
    @GET("pokemon/{name}")
    fun obtainPokemonSearchResult(@Path("name") name: String) : Deferred<PokemonModel>

    @GET
    fun obtainPokemonSearchResultByUrl(@Url url: String) : Deferred<PokemonModel>

    @GET("pokemon")
    fun obtainPokemonSearchResults(@QueryMap options: Map<String, String>) : Deferred<SearchResultsModel>
}