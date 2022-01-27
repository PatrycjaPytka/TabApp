package com.example.tabapp.fragments.listDrug

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
import com.example.tabapp.fragments.Drugmodel.viewmodel.DrugViewModel
import kotlinx.android.synthetic.main.fragment_drug_list.view.*

class DrugListFragment : Fragment() {

    private lateinit var mDrugViewModel: DrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_drug_list, container, false)

        val adapter = ListAdapter()
        val recyclerView = view.drugsRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mDrugViewModel = ViewModelProvider(this).get(DrugViewModel::class.java)
        mDrugViewModel.readAllData.observe(viewLifecycleOwner, Observer {drug ->
            adapter.setData(drug)
        })

        view.AddButton.setOnClickListener {
            findNavController().navigate(R.id.action_drugListFragment_to_drugAddFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllDrugs()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllDrugs() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mDrugViewModel.deleteAllDrugs()
            Toast.makeText(requireContext(), "Wszystkie leki zostały usunięte", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Leki zostaną usunięte.")
        builder.setMessage("Czy napewno chcesz usunąć wszystkie leki?")
        builder.create().show()
    }
}