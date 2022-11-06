package ar.edu.ort.parcialtp3.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceFragment
import androidx.core.content.SharedPreferencesCompat
import androidx.preference.PreferenceFragmentCompat
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
