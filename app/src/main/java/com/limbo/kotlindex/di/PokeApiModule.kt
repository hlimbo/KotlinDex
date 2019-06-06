package com.limbo.kotlindex.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.repository.PokeApiHttpService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class PokeApiModule {
    @Singleton
    @Provides
    fun pokeApiRepository(pokiApiHttpService: PokeApiHttpService, coroutineScope: CoroutineScope): PokeApiHttpRepository {
        return PokeApiHttpRepository(pokiApiHttpService, coroutineScope)
    }

    @Singleton
    @Provides
    fun coroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Singleton
    @Provides // passing retrofit as a parameter.. will allow dagger2 to wire this connection up during code generation process
    fun pokeApiHttpService(retrofit: Retrofit): PokeApiHttpService {
        return retrofit.create(PokeApiHttpService::class.java)
    }

    @Singleton
    @Provides
    fun retrofit(gsonConverterFactory: GsonConverterFactory,
                 coroutineCallAdapterFactory: CoroutineCallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun gson(): Gson {
        return GsonBuilder().setVersion(1.0).create()
    }

    @Singleton
    @Provides
    fun coroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory()
    }
}