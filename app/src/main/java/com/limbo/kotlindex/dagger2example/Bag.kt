package com.limbo.kotlindex.dagger2example

import dagger.Module
import dagger.Provides

// in an actual group setting call this class BagModule
@Module
class Bag {
    @Provides // in an actual group setting call this provides<ClassName>() ~ use case for this would be to inject Repository classes into ViewModel classes
    fun sayLoveDagger2(): Info {
        return Info("Love Dagger 2")
    }
}

class Info constructor(val text: String)