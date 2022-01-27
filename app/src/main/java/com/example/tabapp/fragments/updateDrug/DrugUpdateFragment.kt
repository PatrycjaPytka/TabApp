package com.example.tabapp.fragments.updateDrug

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabapp.R
import com.example.tabapp.fragments.Drugmodel.Drug
import com.example.tabapp.fragments.Drugmodel.viewmodel.DrugViewModel
import kotlinx.android.synthetic.main.fragment_drug_update.*
import kotlinx.android.synthetic.main.fragment_drug_update.view.*


class DrugUpdateFragment : Fragment() {

    private val args by navArgs<DrugUpdateFragmentArgs>()
    private lateinit var mDrugViewModel: DrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_drug_update, container, false)

        mDrugViewModel = ViewModelProvider(this).get(DrugViewModel::class.java)

        view.name_update.setText(args.currentDrug.Name)
        view.dose_update.setText(args.currentDrug.Dose.toString())
        view.daily_doses_update.setText(args.currentDrug.DailyDoses.toString())
        view.period_update.setText(args.currentDrug.Period.toString())
        view.code_update.setText(args.currentDrug.Code)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val Name = name_update.text.toString()
        val Dose = (dose_update.text.toString()).toDouble()
        val DailyDoses = Integer.parseInt(daily_doses_update.text.toString())
        val Period = Integer.parseInt(period_update.text.toString())
        val Code = code_update.text.toString()

        if(inputCheck(Name, dose_update.text, daily_doses_update.text, period_update.text, Code)) {
            val updatedDrug = Drug(args.currentDrug.id, Name, Dose, DailyDoses, Period, Code)
            mDrugViewModel.updateDrug(updatedDrug)
            Toast.makeText(requireContext(), "Lek został zaktualizowany", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_drugUpdateFragment_to_drugListFragment)
        } else {
            Toast.makeText(requireContext(), "Edycja nie powiodła się.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(name: String, dose: Editable, daily_doses: Editable, period: Editable, code: String): Boolean {
        return !(TextUtils.isEmpty(name) && dose.isEmpty() && daily_doses.isEmpty() && period.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteDrug()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDrug() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mDrugViewModel.deleteDrug(args.currentDrug)
            Toast.makeText(requireContext(), "Lek ${args.currentDrug.Name} został usunięty", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_drugUpdateFragment_to_drugListFragment)
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Lek ${args.currentDrug.Name} zostanie usunięty.")
        builder.setMessage("Czy napewno chcesz usunąć lek ${args.currentDrug.Name}?")
        builder.create().show()
    }
}