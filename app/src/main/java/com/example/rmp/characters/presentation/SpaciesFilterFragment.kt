package com.example.rmp.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmp.R
import com.example.rmp.characters.platform.ui.CharactersFilterListener
import com.example.rmp.databinding.FragmentFilterBinding
import org.koin.androidx.scope.ScopeFragment

class SpaciesFilterFragment private constructor(
    private val spacies: Set<String>,
    private val selected: Set<String>,
    private val listener: CharactersFilterListener
) : ScopeFragment(R.layout.fragment_filter) {

    companion object {
        fun newInstance(
            spacies: Set<String>,
            selected: Set<String> = emptySet(),
            listener: CharactersFilterListener
        ): SpaciesFilterFragment {
            return SpaciesFilterFragment(spacies, selected, listener)
        }
    }

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FilterAdapter( spacies, selected, false, listener)
        }
    }
}