package com.limbo.kotlindex.dagger2example

import com.limbo.kotlindex.MainActivity
import dagger.Component

// see https://medium.com/@elye.project/dagger-2-for-dummies-in-kotlin-with-one-page-simple-code-project-618a5f9f2fe8
// Component here is used to connect Bag Module to this MagicBox interface that will be injecting Info into MainActivity
@Component(modules = [Bag::class])
interface MagicBox { // preferably should be called MagicBoxComponent
    // can be called anything (preferably should be called inject method)
    fun poke(app: MainActivity)
}