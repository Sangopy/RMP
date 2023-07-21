package com.example.rmp.characters.platform.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmp.R
import com.example.rmp.characters.presentation.CharactersViewModel
import com.example.rmp.databinding.FragmentCharactersBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment(@LayoutRes layoutResId: Int = R.layout.fragment_characters) :
    ScopeFragment(layoutResId), OnCharacterClickListener {

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = getString(R.string.characters_title)
        _binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        viewModel.getCharactersList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribe() {
        viewModel.charactersList.observe(viewLifecycleOwner) {
            binding.charactersProgressBar.visibility = View.GONE
            binding.charactersRecyclerView.layoutManager = LinearLayoutManager(activity)
            binding.charactersRecyclerView.adapter =
                context?.let { it1 -> CharactersAdapter(it, it1, this) }
        }

        viewModel.characterDetails.observe(viewLifecycleOwner) {

            if (it != null) {
                CharacterDetailsDialogFragment.newInstance(it)
                    .show(parentFragmentManager, CharacterDetailsDialogFragment::javaClass.name)
            }
        }


    }

    override fun onCharacterClicked(id: String) {
        viewModel.getCharacterDetailed(id)
    }

}