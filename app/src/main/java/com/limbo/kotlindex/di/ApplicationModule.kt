package com.limbo.kotlindex.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.limbo.kotlindex.repository.PokeApiHttpService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    fun providesPokeApiHttpService(): PokeApiHttpService {
        val gson: Gson = GsonBuilder().setVersion(1.0).create()
        val retroFitInstance: Retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retroFitInstance.create(PokeApiHttpService::class.java)
    }

//    @Provides
//    fun providesGson(): Gson {
//        return GsonBuilder().setVersion(1.0).create()
//    }
//
//    @Provides
//    fun providesGsonConverterFactory(): GsonConverterFactory {
//        return GsonConverterFactory.create(providesGson())
//    }
//
//    @Provides
//    fun providesRetrofit(baseUrl: String): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(providesGsonConverterFactory())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
//    }
//
//    @Provides
//    fun providesPokeApiService(): PokeApiHttpService {
//        return providesRetrofit("https://pokeapi.co/api/v2/").create(PokeApiHttpService::class.java)
//    }

//    companion object{
//        private const val BASE_URL = "https://pokeapi.co/api/v2/"
//    }
}