package com.limbo.kotlindex.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.limbo.kotlindex.repository.PokeApiHttpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// alternative to using Dagger2 Dependency Injection
class PokeApiServiceFactory {
    companion object {
        fun create(baseUrl: String): PokeApiHttpService {
            val gson: Gson = GsonBuilder().setVersion(1.0).create()
            val retroFitInstance: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retroFitInstance.create(PokeApiHttpService::class.java)
        }
    }
}