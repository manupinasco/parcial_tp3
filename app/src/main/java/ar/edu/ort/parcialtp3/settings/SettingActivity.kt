package ar.edu.ort.parcialtp3.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.preference.PreferenceFragmentCompat
import ar.edu.ort.parcialtp3.MainActivity
import ar.edu.ort.parcialtp3.R


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.user_setting, rootKey)
        }

        /*override fun onPreferenceChange(
            preference: Preference,
            newValue: Any
        ): Boolean {
            return true
        }*/
    }

}
