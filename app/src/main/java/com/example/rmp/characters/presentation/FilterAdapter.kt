package com.example.rmp.characters.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.rmp.R
import com.example.rmp.characters.platform.ui.CharactersFilterListener
import com.example.rmp.databinding.CharacterFilterItemBinding

class FilterAdapter(
    private val items: Set<String>,
    private val selected: Set<String?>,
    private val isGenderFilter: Boolean,
    private val listener: CharactersFilterListener
) : RecyclerView.Adapter<FilterViewHolder>() {

    private val sortedItems = items.sorted()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val rowView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.character_filter_item, parent, false)
        return FilterViewHolder(rowView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.apply {
            val item =  sortedItems.elementAt(position)
            binding.checkbox.text =
                sortedItems.elementAt(position)
            binding.checkbox.tag = sortedItems.elementAt(position)
            binding.checkbox.isChecked = selected.contains(sortedItems.elementAt(position))
            binding.checkbox.setOnClickListener {
                if (isGenderFilter) {
                    listener.onGenderFilterUpdate(
                        sortedItems.elementAt(position),
                        (it as CheckBox).isChecked
                    )
                } else {
                    listener.onSpaciesFilterUpdate(
                        sortedItems.elementAt(position),
                        (it as CheckBox).isChecked
                    )
                }
            }
        }
    }
}

class FilterViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    val binding = CharacterFilterItemBinding.bind(row)
}
