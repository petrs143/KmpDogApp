package cz.cvut.kmp

import cz.cvut.kmp.core.database.getDatabaseBuilder
import org.koin.dsl.module

actual val platformModule = module {
    single { getDatabaseBuilder(context = get()) }
}
