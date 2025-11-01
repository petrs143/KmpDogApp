package cz.cvut.kmp.feature.fact

import cz.cvut.kmp.feature.fact.data.FactLocalDataSource
import cz.cvut.kmp.feature.fact.data.FactRemoteDataSource
import cz.cvut.kmp.feature.fact.data.FactRepositoryImpl
import cz.cvut.kmp.feature.fact.data.ktor.FactRemoteDataSourceImpl
import cz.cvut.kmp.feature.fact.data.room.FactLocalDataSourceImpl
import cz.cvut.kmp.feature.fact.domain.FactRepository
import cz.cvut.kmp.feature.fact.presentation.FactViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val factModule = module {
    single<FactRemoteDataSource> {
        FactRemoteDataSourceImpl(dogApiClient = get())
    }
    single<FactLocalDataSource> {
        FactLocalDataSourceImpl(factDao = get())
    }

    single<FactRepository> {
        FactRepositoryImpl(
            factRemoteDataSource = get(),
            factLocalDataSource = get(),
        )
    }

    viewModelOf(::FactViewModel)
}
