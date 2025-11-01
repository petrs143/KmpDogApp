package cz.cvut.kmp.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * From Docs (https://developer.android.com/kotlin/multiplatform/room#set-up-dependencies):
 * System.getProperty("java.io.tmpdir") points to the temporary folder on the system, which might be cleaned upon reboot.
 * For example, on macOS, you can instead use the ~/Library/Application Support/[your-app] folder.
 */
fun getDatabaseBuilder(): RoomDatabase.Builder<DogDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "dog_database.db")
    return Room.databaseBuilder<DogDatabase>(
        name = dbFile.absolutePath,
    )
}
