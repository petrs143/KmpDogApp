package cz.cvut.kmp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.cvut.kmp.core.database.entity.DbDogFact
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Insert
    suspend fun insert(fact: DbDogFact)

    @Query("SELECT * FROM DbDogFact")
    fun getAll(): Flow<List<DbDogFact>>
}
