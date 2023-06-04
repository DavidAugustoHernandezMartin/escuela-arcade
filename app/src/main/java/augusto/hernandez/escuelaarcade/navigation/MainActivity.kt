package augusto.hernandez.escuelaarcade.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.databinding.ActivityMainBinding
import augusto.hernandez.escuelaarcade.model.AppViewModel
import augusto.hernandez.escuelaarcade.model.states.Resource
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar

/*Una vez que se ha realizado el Login se entra directamente ésta actividad. Básicamente consiste
* en un DrawerlLayout que contiene un componente NavigationDrawer. Éste componente tiene los
* vínculos a los fragmentos de estadísticas, de perfil y el fragmento Home, que será el inicial.*/

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        // Find the DrawerLayout and NavigationView
        val drawerLayout = binding.drawerLayout
        val navigationView = binding.navView

        /*En esta parte se obtiene una referencia de NavHostFragment
         del fragmentContainer de esta actividad. La idea es que se pueda tener acceso
         a su navController*/
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //Se configura el AppBar para el DrawerLayout
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home_fragment, R.id.statistics_fragment,R.id.profile_fragment), drawerLayout)

        // Set up the ActionBar with the NavigationDrawer
        setSupportActionBar(findViewById(R.id.toolbar))

        /*Estos eventos configuran el Home como origen de
        * navegación y la habilitación de regresar a home
        * con el evento up por defecto, respectivamente*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up the NavigationView with the NavController
        navigationView.setupWithNavController(navController)

        /*Esta configuración permite el cierre automático del
        * panel de NavigationDrawer cuando se navege hacia un
        * destino.*/
        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.drawerLayout.closeDrawers()
        }
        // Retrieve the LETTER from the Intent extras
        // intent.extras.getString returns String? (String or null)
        // so toString() guarantees that the value will be a String

        binding.apply {
            app = this@MainActivity
            dataModel = viewModel
        }
        /*En estas implementaciones se trabajará con datos observables desde un viewModel.
        * Ésto permitirá mejor control de la lógica y la conservación de los estados
        * independientemente del ciclo de vida del conponente que los utilice.*/
        viewModel.loginStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.noConnection.root.visibility = View.GONE
                    binding.navHostFragment.visibility = View.VISIBLE
                    Snackbar.make(binding.root, "Sesión sincronizada", Snackbar.LENGTH_LONG).show()

                }
                is Resource.Error -> {
                    // Presentar un AlertDialog para reintentar la operación de inicio de sesión
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Hubo un problema al iniciar sesión. ¿Desea reintentar?")
                        .setNegativeButton("No") { dialog, _ ->
                            binding.navHostFragment.visibility = View.GONE
                            binding.noConnection.root.visibility = View.VISIBLE
                            binding.noConnection.loginLogo.setImageResource(R.drawable.ic_connection_error)
                            binding.noConnection.loginNoConnectionMessage.visibility = View.VISIBLE
                            dialog.dismiss()
                            Snackbar.make(
                                binding.root,
                                resource.message ?: "Error desconocido",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        .setPositiveButton("Sí") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.login()
                        }
                        .show()
                }
                is Resource.Loading -> {
                    // Aquí puedes mostrar un spinner de carga o similar
                    binding.navHostFragment.visibility = View.GONE
                    binding.noConnection.root.visibility = View.VISIBLE
                    binding.noConnection.loginLogo.setImageResource(R.drawable.loading_animation)
                    binding.noConnection.loginNoConnectionMessage.visibility = View.GONE
                }
            }
        }
        viewModel.login()

    }

    fun cerrarSesion(){
        AuthUI.getInstance().signOut(this)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
