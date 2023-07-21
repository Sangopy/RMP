package com.example.rmp.characters.platform.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rmp.R
import com.example.rmp.characters.presentation.CharactersFilterViewModel
import com.example.rmp.characters.presentation.GenderFilterFragment
import com.example.rmp.characters.presentation.SpaciesFilterFragment
import com.example.rmp.databinding.ActivityCharactersFilterBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersFilterActivity : AppCompatActivity(), CharactersFilterListener {

    companion object {
        const val FILTERED_RESULTS = "filteredResults"
        const val FILTER_GENDER = "filterGender"
        const val FILTER_STATUS = "filterStatus"
    }

    private lateinit var binding : ActivityCharactersFilterBinding

    private val viewModel: CharactersFilterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersFilterBinding.inflate(layoutInflater)
        viewModel.genderFilter =
            intent.extras?.getStringArrayList(FILTER_GENDER)?.toMutableSet() ?: mutableSetOf()
        viewModel.spaciesFilter =
            intent.extras?.getStringArrayList(FILTER_STATUS)?.toMutableSet() ?: mutableSetOf()
        viewModel.updateFilterResults()
        setContentView(binding.root)
        initObservers()
        setUpTabLayout()
        binding.closeImageView.setOnClickListener {
            finish()
        }
        binding.seeResultsButton.setOnClickListener { finishWithResult() }
    }

    private fun finishWithResult(reset: Boolean = false) {
        //val gson = Gson()
        val intent = Intent()
        if (!reset) {
           // intent.putExtra(FILTERED_RESULTS, gson.toJson(viewModel.filterResults.value))
        }
        intent.putStringArrayListExtra(FILTER_GENDER, ArrayList(viewModel.genderFilter))
        intent.putStringArrayListExtra(FILTER_STATUS, ArrayList(viewModel.spaciesFilter))
        setResult(RESULT_OK, intent)
        finish()
    }


    private fun initObservers() {
        viewModel.filteredResultsNumber.observe(this) {
            binding.seeResultsButton.text = String.format(
                getString(R.string.myResourcesFiltersButton),
                it
            )
            binding.seeResultsButton.isEnabled = it > 0
        }
    }

    private fun setUpTabLayout() {
        binding.viewPager.adapter = FilterPagerAdapter(this)
        TabLayoutMediator(binding.filterTabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.myResourcesFiltersGender)
                else -> tab.text = getString(R.string.myResourcesFiltersSpacies)
            }
        }.attach()
    }

    override fun onGenderFilterUpdate(gender: String, checked: Boolean) {
        viewModel.updateGenderFilter(gender, checked)
    }

    override fun onSpaciesFilterUpdate(spacies: String, checked: Boolean) {
        viewModel.updateSpaciesFilter(spacies, checked)
    }

    private inner class FilterPagerAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int) = when (position) {
            0 -> GenderFilterFragment.newInstance(
                viewModel.gender,
                viewModel.genderFilter,
                this@CharactersFilterActivity
            )

            else -> SpaciesFilterFragment.newInstance(
                viewModel.spacies,
                viewModel.spaciesFilter,
                this@CharactersFilterActivity
            )
        }

    }
}

interface CharactersFilterListener {
    fun onGenderFilterUpdate(gender: String, checked: Boolean)
    fun onSpaciesFilterUpdate(spacies: String, checked: Boolean)
}