package com.example.rmp.characters.platform.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rmp.R
import com.example.rmp.characters.presentation.CharacterItems
import com.example.rmp.databinding.RowCharacterBinding
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private var charactersList: List<CharacterItems?>,
    private var context: Context,
    private val characterListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_character, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        return holder.bind(charactersList[position]!!)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }



    inner class CharactersViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = RowCharacterBinding.bind(itemView)


        fun bind(item: CharacterItems) {
            binding.speciesNameTextView.text = item.species
            binding.characterNameText.text = item.name
            item.image.let {
                Picasso.with(context)
                    .load(it)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.unknown_image)
                    .error(R.drawable.unknown_image)
                    .into(binding.characterImageView)
            }
            binding.characterCardView.setOnClickListener {
                item.id?.let { id -> characterListener.onCharacterClicked(id) }
            }
        }


    }
}