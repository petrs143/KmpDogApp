package cz.cvut.kmp.feature

import cz.cvut.kmp.feature.breed.breedModule
import cz.cvut.kmp.feature.fact.factModule
import org.koin.dsl.module

val featureModule = module {
    includes(breedModule)
    includes(factModule)
}
