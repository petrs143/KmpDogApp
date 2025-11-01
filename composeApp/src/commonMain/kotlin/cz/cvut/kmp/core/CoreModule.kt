package cz.cvut.kmp.core

import cz.cvut.kmp.core.database.getRoomDatabase
import cz.cvut.kmp.core.network.DogApiClient
import org.koin.dsl.module

val coreModule = module {
    single { DogApiClient() }

    single { getRoomDatabase(builder = get()) }
}
