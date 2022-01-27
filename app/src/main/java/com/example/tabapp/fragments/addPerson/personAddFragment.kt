package com.example.tabapp.fragments.addPerson

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tabapp.R
import com.example.tabapp.fragments.Personmodel.Person
import com.example.tabapp.fragments.Personmodel.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person_add.*
import kotlinx.android.synthetic.main.fragment_person_add.view.*

class personAddFragment : Fragment() {

    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person_add, container, false)
        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        view.save_btn.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val Name = name.text.toString()
        val Surname = surname.text.toString()
        val Phone = phone.text

        if(inputCheck(Name, Surname, Phone)) {
            val person = Person(0, Name, Surname, Integer.parseInt(Phone.toString()))
            mPersonViewModel.addPerson(person)
            Toast.makeText(requireContext(), "Dodano nową osobę bliską", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_personAddFragment_to_personListFragment)
        } else {
            Toast.makeText(requireContext(), "Wprowadzono niepoprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, surname: String, phone: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(surname) && phone.isEmpty())
    }
}