package com.example.spygamers.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spygamers.db.schemas.Gamer
import com.example.spygamers.db.schemas.PermissionsGrants
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Gamer::class, PermissionsGrants::class], version = 5)
abstract class GamerDatabase : RoomDatabase() {
    abstract fun gamerDao(): GamerDao

    companion object {
        @Volatile
        private var INSTANCE: GamerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): GamerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GamerDatabase::class.java,
                    "GamerDatabase"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}