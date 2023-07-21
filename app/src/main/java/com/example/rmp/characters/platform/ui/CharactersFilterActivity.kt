package com.example.rmp.characters.platform.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rmp.R
import com.example.rmp.characters.presentation.Character.CharactersViewModel
import com.example.rmp.characters.presentation.filter.GenderFilterFragment
import com.example.rmp.characters.presentation.filter.StatusFilterFragment
import com.example.rmp.databinding.ActivityCharactersFilterBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersFilterActivity : AppCompatActivity(), CharactersFilterListener {

    companion object {
        const val FILTERED_RESULTS = "filteredResults"
        const val FILTER_GENDER = "filterGender"
        const val FILTER_STATUS = "filterStatus"
    }

    private lateinit var binding : ActivityCharactersFilterBinding

    private val viewModel: CharactersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersFilterBinding.inflate(layoutInflater)
        viewModel.genderFilter =
            intent.extras?.getStringArrayList(FILTER_GENDER)?.toMutableSet() ?: mutableSetOf()
        viewModel.statusFilter =
            intent.extras?.getStringArrayList(FILTER_STATUS)?.toMutableSet() ?: mutableSetOf()
        viewModel.updateFilterResults()
        setContentView(binding.root)
        initObservers()
        setUpTabLayout()
        binding.resetFiltersButton.paintFlags = binding.resetFiltersButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.closeImageView.setOnClickListener {
            finish()
        }
        binding.seeResultsButton.setOnClickListener { finishWithResult() }
        binding.resetFiltersButton.setOnClickListener { onResetFiltersClicked() }
    }

    private fun finishWithResult(reset: Boolean = false) {
        val gson = Gson()
        val intent = Intent()
        if (!reset) {
            intent.putExtra(FILTERED_RESULTS, gson.toJson(viewModel.filterResults.value))
        }
        intent.putStringArrayListExtra(FILTER_GENDER, ArrayList(viewModel.genderFilter))
        intent.putStringArrayListExtra(FILTER_STATUS, ArrayList(viewModel.statusFilter))
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onResetFiltersClicked(view: View? = null) {
        when(viewModel.isFilterApplied) {
            true -> {
                viewModel.reset()
                finishWithResult(reset = true)
            }
            false -> {
                viewModel.reset()
                finishWithResult(reset = true)
            }
        }
    }


    private fun initObservers() {
        viewModel.filteredResultsNumber.observe(this) {
            binding.seeResultsButton.text = String.format(
                getString(R.string.filtersButton),
                it
            )
            binding.seeResultsButton.isEnabled = it > 0
        }
    }

    private fun setUpTabLayout() {
        binding.viewPager.adapter = FilterPagerAdapter(this)
        TabLayoutMediator(binding.filterTabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.filtersGender)
                else -> tab.text = getString(R.string.filtersStatus)
            }
        }.attach()
    }

    override fun onGenderFilterUpdate(gender: String, checked: Boolean) {
        viewModel.updateGenderFilter(gender, checked)
    }

    override fun onStatusFilterUpdate(status: String, checked: Boolean) {
        viewModel.updateStatusFilter(status, checked)
    }

    private inner class FilterPagerAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int) = when (position) {

            0 -> GenderFilterFragment.newInstance(
                viewModel.getGenderList(),
                viewModel.genderFilter,
                this@CharactersFilterActivity
            )

            else -> StatusFilterFragment.newInstance(
                viewModel.getStatusList(),
                viewModel.statusFilter,
                this@CharactersFilterActivity
            )
        }

    }
}

interface CharactersFilterListener {
    fun onGenderFilterUpdate(gender: String, checked: Boolean)
    fun onStatusFilterUpdate(status: String, checked: Boolean)
}