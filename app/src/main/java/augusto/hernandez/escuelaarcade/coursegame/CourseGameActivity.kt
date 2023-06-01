package augusto.hernandez.escuelaarcade.coursegame

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.databinding.ActivityCoursegameBinding
import augusto.hernandez.escuelaarcade.model.Curso
import augusto.hernandez.escuelaarcade.model.states.Resource
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class CourseGameActivity:AppCompatActivity() {
    lateinit var binding: ActivityCoursegameBinding
    lateinit var curso: Curso
    private lateinit var viewModel:CourseGameViewModel
    private lateinit var navController: NavController
    companion object{
        const val CURSO = "CURSO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursegameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        curso = intent.getParcelableExtra<Curso>(CURSO) as Curso
        viewModel = ViewModelProvider(this)[CourseGameViewModel::class.java]
        viewModel.curso.value = curso
        viewModel.userID = FirebaseAuth.getInstance().currentUser!!.uid


        // En esta parte se obtiene una referencia de NavHostFragment
        // del fragmentContainer de esta actividad. La idea es que se pueda tener acceso
        // a su navController.
        val courseFragmentContainer = supportFragmentManager.findFragmentById(R.id.courseGameFragmentContainer) as NavHostFragment
        navController = courseFragmentContainer.navController

        // Configurar el AppBar
        setSupportActionBar(findViewById(R.id.courseGameToolbar))

        /*Estos eventos configuran el Home como origen de
        * navegación y la habilitación de regresar a home
        * con el evento up por defecto, respectivamente*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Configurar el ActionBar para trabajar con NavController
        setupActionBarWithNavController(this,navController)

        // Manejar el evento de navegación hacia atrás.
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(navController.previousBackStackEntry != null)
        }

        /*Se gestiona la renderización de las vistas en concordancia con los contenidos recuperados
      * de Firebase*/
        viewModel.fetchStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.noContent.root.visibility = View.GONE
                    binding.courseGameFragmentContainer.visibility = View.VISIBLE
                    Snackbar.make(binding.root, "Datos sincronizados", Snackbar.LENGTH_LONG).show()

                }
                is Resource.Error -> {
                    /*Presentar un AlertDialog para reintentar la operación de obtención
                    de contenidos del curso*/
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Hubo un problema al solicitar datos del curso. ¿Desea reintentar?")
                        .setNegativeButton("No") { dialog, _ ->
                            binding.courseGameFragmentContainer.visibility = View.GONE
                            binding.noContent.root.visibility = View.VISIBLE
                            binding.noContent.loginLogo.setImageResource(R.drawable.ic_connection_error)
                            binding.noContent.loginNoConnectionMessage.visibility = View.VISIBLE
                            dialog.dismiss()
                            Snackbar.make(
                                binding.root,
                                resource.message ?: "Error desconocido",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        .setPositiveButton("Sí") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.fetch()
                        }
                        .show()
                }
                is Resource.Loading -> {
                    // Aquí se muestra el spinner de carga por defecto
                    binding.courseGameFragmentContainer.visibility = View.GONE
                    binding.noContent.root.visibility = View.VISIBLE
                    binding.noContent.loginLogo.setImageResource(R.drawable.loading_animation)
                    binding.noContent.loginNoConnectionMessage.visibility = View.GONE
                }
            }
        }
        viewModel.fetch()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}