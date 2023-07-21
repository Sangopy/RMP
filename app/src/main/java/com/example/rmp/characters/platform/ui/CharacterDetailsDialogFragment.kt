package com.example.rmp.characters.platform.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmp.R
import com.example.rmp.characters.presentation.Character.CharacterDetailsItems
import com.example.rmp.characters.presentation.Character.CharacterFieldItem
import com.example.rmp.databinding.CharacterDetailsFragmentDialogBinding
import com.squareup.picasso.Picasso

class CharacterDetailsDialogFragment : DialogFragment() {

    companion object {

        fun newInstance(
            characterDetailsItems: CharacterDetailsItems,
        ): CharacterDetailsDialogFragment {
            val fragment = CharacterDetailsDialogFragment()
            fragment.characterDetailsItems = characterDetailsItems
            return fragment
        }
    }

    private var _binding: CharacterDetailsFragmentDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var characterDetailsItems: CharacterDetailsItems

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterDetailsFragmentDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val width = resources.displayMetrics.widthPixels // Obtenez la largeur de l'écran
        val height = resources.displayMetrics.heightPixels // Obtenez la hauteur de l'écran
        dialog?.window?.setLayout(
            (width * 0.8).toInt(),
            (height * 0.8).toInt()
        ) // Définir la taille personnalisée du Dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterDetailsItems.image.let {
            Picasso.with(context)
                .load(it)
                .centerCrop()
                .fit()
                .placeholder(R.drawable.unknown_image)
                .error(R.drawable.unknown_image)
                .into(binding.characterImageView)
        }

        buildCharacterDetails(characterDetailsItems)
        binding.characterDetailsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.characterDetailsRecyclerView.adapter =
            CharacterDetailsAdapter(buildCharacterDetails(characterDetailsItems))
    }

    private fun buildCharacterDetails(characterDetailsItems: CharacterDetailsItems): ArrayList<CharacterFieldItem> {
        val characterTable = arrayListOf<CharacterFieldItem>()
        characterTable.add(
            CharacterFieldItem(
                if (characterDetailsItems.name.isNullOrBlank()) resources.getString(R.string.unknown) else characterDetailsItems.name,
                resources.getString(R.string.character_name)
            )
        )
        characterTable.add(
            CharacterFieldItem(
                if (characterDetailsItems.gender.isNullOrBlank()) resources.getString(R.string.unknown) else characterDetailsItems.gender,
                resources.getString(R.string.character_gender)
            )
        )
        characterTable.add(
            CharacterFieldItem(
                if (characterDetailsItems.species.isNullOrBlank()) resources.getString(R.string.unknown) else characterDetailsItems.species,
                resources.getString(R.string.character_species)
            )
        )
        characterTable.add(
            CharacterFieldItem(
                if (characterDetailsItems.status.isNullOrBlank()) resources.getString(R.string.unknown) else characterDetailsItems.status,
                resources.getString(R.string.character_status)
            )
        )
        characterTable.add(
            CharacterFieldItem(
                if (characterDetailsItems.type.isNullOrBlank()) resources.getString(R.string.unknown) else characterDetailsItems.type,
                resources.getString(R.string.character_type)
            )
        )

        return characterTable
    }
}


