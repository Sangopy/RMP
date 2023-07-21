package com.example.rmp.home.platform.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.rmp.R
import com.example.rmp.characters.platform.ui.CharactersFilterActivity
import com.example.rmp.characters.platform.ui.CharactersFilterActivity.Companion.FILTERED_RESULTS
import com.example.rmp.characters.platform.ui.CharactersFragment
import com.example.rmp.characters.presentation.CharactersFilterViewModel
import com.example.rmp.characters.presentation.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val charactersFilterViewModel: CharactersFilterViewModel by viewModel()
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val filteredList = result.data?.getStringExtra(FILTERED_RESULTS)
                    val filterGender =
                        result.data?.getStringArrayListExtra("FILTER_GENDER") ?: emptyList<String>()
                    val filterSpacies =
                        result.data?.getStringArrayListExtra("FILTER_SPACIES") ?: emptyList<String>()

                    charactersFilterViewModel.genderFilter = filterGender.toMutableSet()
                    charactersFilterViewModel.spaciesFilter = filterSpacies.toMutableSet()
                }
            }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharactersFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.filter, menu)
        val filterAction =
            menu.findItem(R.id.action_filter)
        filterAction.isVisible = supportFragmentManager.findFragmentById(R.id.container) is CharactersFragment
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retValue = true
        when (item.itemId) {
            R.id.action_filter -> {
                val intent = Intent(this, CharactersFilterActivity::class.java)
                intent.putStringArrayListExtra(
                    "FILTER_GENDER",
                    ArrayList(charactersFilterViewModel.genderFilter)
                )
                intent.putStringArrayListExtra(
                    "FILTER_SPACIES",
                    ArrayList(charactersFilterViewModel.spaciesFilter)
                )
                startForResult.launch(intent)
            }

            else -> {
                retValue = super.onOptionsItemSelected(item)

            }
        }
        return retValue
    }



}