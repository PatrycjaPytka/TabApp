package com.example.tabapp.fragments.Personmodel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tabapp.data.PersonDatabase
import com.example.tabapp.fragments.Personmodel.repository.PersonRepository
import com.example.tabapp.fragments.Personmodel.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Person>>
    private val repository: PersonRepository

    init {
        val personDao = PersonDatabase.getDatabase(application).personDao()
        repository = PersonRepository(personDao)
        readAllData = repository.readAllData
    }

    fun addPerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPerson(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePerson(person)
        }
    }

    fun deleteAllPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPersons()
        }
    }

     fun loadAllPhones(): List<Int> {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.loadAllPhones()
//        }
         return repository.loadAllPhones()
     }
}