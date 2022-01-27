package com.example.tabapp.fragments.updatePerson

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabapp.R
import com.example.tabapp.fragments.Personmodel.Person
import com.example.tabapp.fragments.Personmodel.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person_update.*
import kotlinx.android.synthetic.main.fragment_person_update.view.*


class personUpdateFragment : Fragment() {

    private val args by navArgs<personUpdateFragmentArgs>()
    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person_update, container, false)

        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        view.name_update.setText(args.currentPerson.Name)
        view.surname_update.setText(args.currentPerson.Surname)
        view.phone_update.setText(args.currentPerson.Phone.toString())

        view.update_btn.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val Name = name_update.text.toString()
        val Surname = surname_update.text.toString()
        val Phone = Integer.parseInt(phone_update.text.toString())

        if(inputCheck(Name, Surname, phone_update.text)) {
            val updatedPerson = Person(args.currentPerson.id, Name, Surname, Phone)
            mPersonViewModel.updatePerson(updatedPerson)
            Toast.makeText(requireContext(), "Osoba została zaktualizowana", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_personUpdateFragment_to_personListFragment)
        } else {
            Toast.makeText(requireContext(), "Edycja nie powiodła się.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(name: String, surname: String, phone: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(surname) && phone.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deletePerson()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePerson() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mPersonViewModel.deletePerson(args.currentPerson)
            Toast.makeText(requireContext(), "Osoba: ${args.currentPerson.Name} ${args.currentPerson.Surname} została usunięta", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_personUpdateFragment_to_personListFragment)
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Osoba: ${args.currentPerson.Name} ${args.currentPerson.Surname} zostanie usunięta.")
        builder.setMessage("Czy napewno chcesz usunąć osobę: ${args.currentPerson.Name} ${args.currentPerson.Surname}?")
        builder.create().show()
    }
}