package com.example.tabapp.fragments.Personmodel.repository

import androidx.lifecycle.LiveData
import com.example.tabapp.data.PersonDao
import com.example.tabapp.fragments.Personmodel.Person

class PersonRepository(private val personDao: PersonDao) {

    val readAllData: LiveData<List<Person>> = personDao.readAllData()

    suspend fun addPerson(person: Person) {
        personDao.addPerson(person)
    }

    suspend fun updatePerson(person: Person) {
        personDao.updatePerson(person)
    }

    suspend fun deletePerson(person: Person) {
        personDao.deletePerson(person)
    }

    suspend fun deleteAllPersons() {
        personDao.deleteAllPersons()
    }

    fun loadAllPhones(): List<Int> {
        return personDao.loadAllPhones()
    }
}