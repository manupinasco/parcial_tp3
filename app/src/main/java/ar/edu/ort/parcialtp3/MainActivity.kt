package ar.edu.ort.parcialtp3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import android.view.WindowManager
import android.widget.Toast
import ar.edu.ort.parcialtp3.backmethod.IOnBackPressed
import ar.edu.ort.parcialtp3.characters.DetailsFragment
import ar.edu.ort.parcialtp3.usersession.UserSession

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

        navigationView.menu.findItem(R.id.loginFragment).setOnMenuItemClickListener {
            logout()
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
            supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                supportActionBar?.hide()
            }  else {
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