package cz.cvut.kmp

import cz.cvut.kmp.core.coreModule
import cz.cvut.kmp.feature.featureModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    includes(featureModule)
    includes(platformModule)
}

expect val platformModule: Module
