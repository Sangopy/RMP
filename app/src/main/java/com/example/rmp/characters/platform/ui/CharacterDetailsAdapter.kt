package com.example.rmp.characters.platform.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rmp.R
import com.example.rmp.characters.presentation.Character.CharacterFieldItem
import com.example.rmp.databinding.RowCharacterDetailsBinding

class CharacterDetailsAdapter(private var characterDetails: ArrayList<CharacterFieldItem>) :
    RecyclerView.Adapter<CharacterDetailsAdapter.CharacterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_character_details, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterDetails.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        return holder.bind(characterDetails[position])
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = RowCharacterDetailsBinding.bind(itemView)

        fun bind(itemRow: CharacterFieldItem) {
            binding.characterField.text = itemRow.label
            binding.characterDescription.text = itemRow.item
        }

    }

}