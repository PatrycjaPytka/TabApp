package com.example.tabapp.fragments.listAidDrug

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapp.R
import com.example.tabapp.fragments.AidDrugModel.AidDrug
import kotlinx.android.synthetic.main.custom_aid_row.view.*

class AidListAdapter: RecyclerView.Adapter<AidListAdapter.AidViewHolder>() {

    private var aiddrugList = emptyList<AidDrug>()

    class AidViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AidViewHolder {
        return AidViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_aid_row, parent, false))
    }

    override fun getItemCount(): Int {
        return aiddrugList.size
    }

    override fun onBindViewHolder(holder: AidViewHolder, position: Int) {
        val currentItem = aiddrugList[position]
//        holder.itemView.aid_id.text = currentItem.id.toString()
        holder.itemView.aid_name.text = currentItem.Name
        holder.itemView.aid_amount.text = currentItem.Amount.toString()

        holder.itemView.AidrowLayout.setOnClickListener {
            val action = ListAidDrugFragmentDirections.actionListAidDrugFragmentToUpdateAidDrugFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(aiddrug: List<AidDrug>) {
        this.aiddrugList = aiddrug
        notifyDataSetChanged()
    }
}