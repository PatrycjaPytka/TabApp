package com.example.tabapp.fragments.listPerson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapp.R
import com.example.tabapp.fragments.Personmodel.Person
import kotlinx.android.synthetic.main.custom_person_row.view.*

class PersonListAdapter: RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>() {

    private var personList = emptyList<Person>()

    class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_person_row, parent, false))
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentItem = personList[position]
//        holder.itemView.person_id.text = currentItem.id.toString()
        holder.itemView.person_name.text = currentItem.Name
        holder.itemView.person_surname.text = currentItem.Surname
        holder.itemView.person_phone.text = currentItem.Phone.toString()

        holder.itemView.PersonrowLayout.setOnClickListener {
            val action = personListFragmentDirections.actionPersonListFragmentToPersonUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(person: List<Person>) {
        this.personList = person
        notifyDataSetChanged()
    }
}