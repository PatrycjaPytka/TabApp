package com.example.tabapp.data

import android.content.PeriodicSync
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabapp.fragments.Personmodel.Person


@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPerson(person: Person)

    @Query("SELECT * FROM person_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Person>>

    @Update
    fun updatePerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("DELETE FROM person_table")
    fun deleteAllPersons()

    @Query("SELECT Phone FROM person_table")
    fun loadAllPhones(): List<Int>
}