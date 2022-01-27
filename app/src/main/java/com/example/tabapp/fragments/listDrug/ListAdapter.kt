package com.example.tabapp.fragments.listDrug

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapp.R
import com.example.tabapp.fragments.Drugmodel.Drug
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var drugList = emptyList<Drug>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return drugList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = drugList[position]
//        holder.itemView.drug_id.text = currentItem.id.toString()
        holder.itemView.drug_name.text = currentItem.Name
        holder.itemView.drug_dose.text = currentItem.Dose.toString()
        holder.itemView.drug_daily_doses.text = currentItem.DailyDoses.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = DrugListFragmentDirections.actionDrugListFragmentToDrugUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(drug: List<Drug>) {
        this.drugList = drug
        notifyDataSetChanged()
    }
}