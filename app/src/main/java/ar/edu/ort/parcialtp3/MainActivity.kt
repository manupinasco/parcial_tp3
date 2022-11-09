package ar.edu.ort.parcialtp3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed
import ar.edu.ort.parcialtp3.characters.DetailsFragment
import ar.edu.ort.parcialtp3.settings.SettingsActivity
import ar.edu.ort.parcialtp3.usersession.UserSession
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    //private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    //private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Obtengo el Control de Navegacion
        drawerLayout = findViewById(R.id.drawer_layout_id)
        navigationView = findViewById(R.id.nav_view)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        setUpDrawerLayout()
        supportActionBar?.setDisplayShowTitleEnabled(false);

        navigationView.menu.findItem(R.id.loginFragment).setOnMenuItemClickListener {
            logout()
            true
        }

        navigationView.menu.findItem(R.id.settingsActivity).setOnMenuItemClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    private fun setUpDrawerLayout() {

        val navController = navHostFragment.navController
        //Conecto la navegacion de drawer_menu con el del nav_graph
        navigationView.setupWithNavController(navController)

        //Configuramos la AppBar para aue muestre el icono del drawer_menu y actualice el titulo
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

        //Listener para cuando se realiza la navegacion
        navController.addOnDestinationChangedListener{_,destination,_ ->
            //Mi icono izquierdo de la appBar va a ser el hamburger en drawable
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                supportActionBar?.hide()
            }  else {
                if (destination.id == R.id.detailsFragment){
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                } else {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
                }
                supportActionBar?.show()
            }
        }


    }
    //Habilitar Navegacion desde la appbar con el Drawer
    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                if(fragment is DetailsFragment ) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    onBackPressed()
                }
            }
        }

        return false //NavigationUI.navigateUp(navHostFragment.navController, drawerLayout)
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        Log.d("Test", prefs.getString("edit_text_preference_1","").toString())
        Log.d("Test", prefs.getBoolean("enableFav",false).toString())

    }

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                val isCanceled = (fragment as? IOnBackPressed)?.onBackPressed()
                if(isCanceled != null) {
                    if (!isCanceled) {
                        super.onBackPressed()
                    }
                }
                else {
                    super.onBackPressed()
                }
            }
        }
    }

    fun logout() {
        val navController = findNavController(R.id.fragmentContainerView)
        UserSession.idUser = -1
        navController.navigateUp() // to clear previous navigation history
        navController.navigate(R.id.loginFragment)
        drawerLayout.closeDrawer(GravityCompat.START)
    }


}