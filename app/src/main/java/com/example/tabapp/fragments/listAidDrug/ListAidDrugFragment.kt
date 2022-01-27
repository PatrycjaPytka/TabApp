package com.example.tabapp.fragments.listAidDrug

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
import com.example.tabapp.fragments.AidDrugModel.viewmodel.AidDrugViewModel
import kotlinx.android.synthetic.main.fragment_list_aid_drug.view.*


class ListAidDrugFragment : Fragment() {

    private lateinit var mAidDrugViewModel: AidDrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_aid_drug, container, false)

        val adapter = AidListAdapter()
        val recyclerView = view.aiddrugsRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mAidDrugViewModel = ViewModelProvider(this).get(AidDrugViewModel::class.java)
        mAidDrugViewModel.readAllData.observe(viewLifecycleOwner, Observer {aiddrug ->
            adapter.setData(aiddrug)
        })

        view.AddAidDrugButton.setOnClickListener {
            findNavController().navigate(R.id.action_listAidDrugFragment_to_addAidDrugFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_aid_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_aid_delete) {
            deleteAllAidDrugs()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllAidDrugs() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mAidDrugViewModel.deleteAllAidDrugs()
            Toast.makeText(requireContext(), "Wszystkie leki zostały usunięte", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Leki zostaną usunięte.")
        builder.setMessage("Czy napewno chcesz usunąć wszystkie leki?")
        builder.create().show()
    }
}