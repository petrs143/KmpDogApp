package cz.cvut.kmp.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<DogDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("dog_database.db")
    return Room.databaseBuilder<DogDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
