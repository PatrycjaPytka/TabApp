package com.example.tabapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabapp.fragments.Drugmodel.Drug


@Database(entities = [Drug::class], version = 1, exportSchema = false)
abstract class DrugDatabase: RoomDatabase() {

    abstract fun drugDao(): DrugDao

    companion object {
        @Volatile
        private var INSTANCE: DrugDatabase? = null

        fun getDatabase(context: Context): DrugDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrugDatabase::class.java,
                    "drug_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}