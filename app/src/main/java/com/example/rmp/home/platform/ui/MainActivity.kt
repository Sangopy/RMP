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
import com.example.rmp.characters.platform.ui.CharactersFilterActivity.Companion.FILTER_GENDER
import com.example.rmp.characters.platform.ui.CharactersFilterActivity.Companion.FILTER_STATUS
import com.example.rmp.characters.platform.ui.CharactersFragment
import com.example.rmp.characters.presentation.Character.CharacterItems
import com.example.rmp.characters.presentation.Character.CharactersViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val charactersViewModel: CharactersViewModel by viewModel()
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
                        result.data?.getStringArrayListExtra(FILTER_GENDER) ?: emptyList<String>()
                    val filterStatus =
                        result.data?.getStringArrayListExtra(FILTER_STATUS) ?: emptyList<String>()


                    val filteredCharacters = filteredList?.let {
                        Gson().fromJson(
                            filteredList,
                            Array<CharacterItems?>::class.java
                        ).toList()
                    } ?: charactersViewModel.charactersList.value

                    charactersViewModel.filterResults.value = filteredCharacters
                    charactersViewModel.genderFilter = filterGender.toMutableSet()
                    charactersViewModel.statusFilter = filterStatus.toMutableSet()
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
        filterAction.isVisible =
            supportFragmentManager.findFragmentById(R.id.container) is CharactersFragment
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retValue = true
        when (item.itemId) {
            R.id.action_filter -> {
                val intent = Intent(this, CharactersFilterActivity::class.java)
                intent.putStringArrayListExtra(
                    FILTER_GENDER,
                    ArrayList(charactersViewModel.genderFilter)
                )
                intent.putStringArrayListExtra(
                    FILTER_STATUS,
                    ArrayList(charactersViewModel.statusFilter)
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