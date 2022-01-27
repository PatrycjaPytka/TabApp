package com.example.tabapp.fragments.addAidDrug

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
import com.example.tabapp.fragments.AidDrugModel.AidDrug
import com.example.tabapp.fragments.AidDrugModel.viewmodel.AidDrugViewModel
import kotlinx.android.synthetic.main.fragment_add_aid_drug.*
import kotlinx.android.synthetic.main.fragment_add_aid_drug.view.*


class AddAidDrugFragment : Fragment() {

    private lateinit var mAidDrugViewModel: AidDrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_aid_drug, container, false)
        mAidDrugViewModel = ViewModelProvider(this).get(AidDrugViewModel::class.java)

        view.save_drug_aid_btn.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val Name = aid_name.text.toString()
        val Amount = aid_amount.text

        if(inputCheck(Name, Amount)) {
            val aiddrug = AidDrug(0, Name, Integer.parseInt(Amount.toString()))
            mAidDrugViewModel.addAidDrug(aiddrug)
            Toast.makeText(requireContext(), "Dodano nowy lek", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addAidDrugFragment_to_listAidDrugFragment)
        } else {
            Toast.makeText(requireContext(), "Wprowadzono niepoprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, amount: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && amount.isEmpty())
    }
}