package cz.cvut.kmp.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import cz.cvut.kmp.core.database.entity.DbDogFact

@Database(entities = [DbDogFact::class], version = 1)
@ConstructedBy(DogDatabaseConstructor::class)
abstract class DogDatabase : RoomDatabase() {

}

@Suppress("KotlinNoActualForExpect")
expect object DogDatabaseConstructor : RoomDatabaseConstructor<DogDatabase> {

    override fun initialize(): DogDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<DogDatabase>
): DogDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
