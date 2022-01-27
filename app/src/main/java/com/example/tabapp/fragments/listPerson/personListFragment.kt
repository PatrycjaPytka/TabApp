package com.example.tabapp.fragments.listPerson

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tabapp.R
import com.example.tabapp.fragments.Personmodel.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person_list.view.*


class personListFragment : Fragment() {

    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)

        val adapter = PersonListAdapter()
        val recyclerView = view.personRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        mPersonViewModel.readAllData.observe(viewLifecycleOwner, Observer {person ->
            adapter.setData(person)
        })

        view.addPersonButton.setOnClickListener {
            findNavController().navigate(R.id.action_personListFragment_to_personAddFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllPersons()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllPersons() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mPersonViewModel.deleteAllPersons()
            Toast.makeText(requireContext(), "Wszystkie osoby bliskie zostały usunięte", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Osoby bliskie zostaną usunięte.")
        builder.setMessage("Czy napewno chcesz usunąć wszystkie osoby bliskie?")
        builder.create().show()
    }
}