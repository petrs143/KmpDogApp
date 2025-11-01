package cz.cvut.kmp.feature.fact.data.room

import cz.cvut.kmp.core.database.dao.FactDao
import cz.cvut.kmp.feature.fact.data.FactLocalDataSource
import cz.cvut.kmp.feature.fact.domain.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FactLocalDataSourceImpl(
    private val factDao: FactDao,
) : FactLocalDataSource {

    private val dbFactMapper = DbFactMapper()

    override fun getFacts(): Flow<List<Fact>> {
        return factDao.getAll().map {
            it.map(dbFactMapper::map)
        }
    }

    override suspend fun saveFact(fact: Fact) {
        return factDao.insert(dbFactMapper.map(fact))
    }
}
