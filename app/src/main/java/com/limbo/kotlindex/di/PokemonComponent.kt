package com.limbo.kotlindex.di

import com.limbo.kotlindex.fragments.BaseFragment
import com.limbo.kotlindex.repository.PokeApiHttpRepository
import com.limbo.kotlindex.repository.PokeApiHttpService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PokeApiModule::class])
interface PokemonComponent {
//    fun getPokeApiHttpService(): PokeApiHttpService
//    fun getPokeApiRepository(): PokeApiHttpRepository
    fun inject(fragment: BaseFragment)
}