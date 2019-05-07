package com.limbo.kotlindex

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeApiServiceFactory {
    companion object {
        fun create(baseUrl: String): PokeApiHttpService {
            val gson: Gson = GsonBuilder().setVersion(1.0).create()
            val retroFitInstance: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retroFitInstance.create(PokeApiHttpService::class.java)
        }
    }
}