package cz.cvut.kmp.feature.breed

import cz.cvut.kmp.feature.breed.data.BreedRemoteDataSource
import cz.cvut.kmp.feature.breed.data.BreedRepositoryImpl
import cz.cvut.kmp.feature.breed.data.ktor.BreedRemoteDataSourceImpl
import cz.cvut.kmp.feature.breed.domain.BreedRepository
import cz.cvut.kmp.feature.breed.presentation.BreedListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val breedModule = module {
    single<BreedRemoteDataSource> { BreedRemoteDataSourceImpl(apiClient = get()) }
    single<BreedRepository> { BreedRepositoryImpl(breedRemoteDataSource = get()) }

    viewModelOf(::BreedListViewModel)
}
