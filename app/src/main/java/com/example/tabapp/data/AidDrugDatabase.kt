package com.example.tabapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabapp.fragments.AidDrugModel.AidDrug

@Database(entities = [AidDrug::class], version = 1, exportSchema = false)
abstract class AidDrugDatabase: RoomDatabase() {

    abstract fun aiddrugDao(): AidDrugDao

    companion object {
        @Volatile
        private var INSTANCE: AidDrugDatabase? = null

        fun getDatabase(context: Context): AidDrugDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AidDrugDatabase::class.java,
                    "aid_drug_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}