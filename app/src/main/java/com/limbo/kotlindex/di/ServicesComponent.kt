package com.limbo.kotlindex.di

import com.limbo.kotlindex.MainApplication
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ServicesComponent {
    fun inject(application: MainApplication)
}