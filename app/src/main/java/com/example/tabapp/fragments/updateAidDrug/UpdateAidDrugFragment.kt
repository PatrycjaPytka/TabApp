package com.example.tabapp.fragments.updateAidDrug

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
import com.example.tabapp.fragments.AidDrugModel.AidDrug
import com.example.tabapp.fragments.AidDrugModel.viewmodel.AidDrugViewModel
import kotlinx.android.synthetic.main.fragment_update_aid_drug.*
import kotlinx.android.synthetic.main.fragment_update_aid_drug.view.*


class UpdateAidDrugFragment : Fragment() {

    private val args by navArgs<UpdateAidDrugFragmentArgs>()
    private lateinit var mAidDrugViewModel: AidDrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_aid_drug, container, false)

        mAidDrugViewModel = ViewModelProvider(this).get(AidDrugViewModel::class.java)

        view.aid_name_update.setText(args.currentAidDrug.Name)
        view.aid_amount_update.setText(args.currentAidDrug.Amount.toString())

        view.update_drug_aid_btn.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val Name = aid_name_update.text.toString()
        val Amount = Integer.parseInt(aid_amount_update.text.toString())

        if(inputCheck(Name, aid_amount_update.text)) {
            val updatedAidDrug = AidDrug(args.currentAidDrug.id, Name, Amount)
            mAidDrugViewModel.updateAidDrug(updatedAidDrug)
            Toast.makeText(requireContext(), "Lek został zaktualizowany", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateAidDrugFragment_to_listAidDrugFragment)
        } else {
            Toast.makeText(requireContext(), "Edycja nie powiodła się.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(name: String, amount: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && amount.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_aid_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_aid_delete) {
            deleteAidDrug()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAidDrug() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mAidDrugViewModel.deleteAidDrug(args.currentAidDrug)
            Toast.makeText(requireContext(), "Lek ${args.currentAidDrug.Name} został usunięty", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateAidDrugFragment_to_listAidDrugFragment)
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Lek ${args.currentAidDrug.Name} zostanie usunięty.")
        builder.setMessage("Czy napewno chcesz usunąć lek ${args.currentAidDrug.Name}?")
        builder.create().show()
    }
}