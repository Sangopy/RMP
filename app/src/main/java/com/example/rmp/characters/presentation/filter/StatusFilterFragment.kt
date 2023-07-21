package com.example.rmp.characters.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmp.R
import com.example.rmp.characters.platform.ui.CharactersFilterListener
import com.example.rmp.databinding.FragmentFilterBinding
import org.koin.androidx.scope.ScopeFragment

class StatusFilterFragment private constructor(
    private val status: Set<String?>,
    private val selected: Set<String>,
    private val listener: CharactersFilterListener
) : ScopeFragment(R.layout.fragment_filter) {

    companion object {
        fun newInstance(
            status: Set<String?>,
            selected: Set<String> = emptySet(),
            listener: CharactersFilterListener
        ): StatusFilterFragment {
            return StatusFilterFragment(status, selected, listener)
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
            adapter = FilterAdapter(status as Set<String>, selected, false, listener)
        }
    }
}