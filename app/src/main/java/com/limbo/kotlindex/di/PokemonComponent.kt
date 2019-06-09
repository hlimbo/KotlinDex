package com.limbo.kotlindex.di

import com.limbo.kotlindex.fragments.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PokeApiModule::class])
interface PokemonComponent {
    // explicitly exposes which objects you want to get from PokeApiModule
//    fun getPokeApiHttpService(): PokeApiHttpService
//    fun getPokeApiRepository(): PokeApiHttpRepository

    // inject all dependencies being handled in PokeApiModule class into BaseFragment
    // must look into PokeApiModule class to see which objects can be @Inject-ed into BaseFragment class
    fun inject(fragment: BaseFragment)
}