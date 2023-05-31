package augusto.hernandez.escuelaarcade.coursegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.databinding.ActivityCoursegameBinding
import augusto.hernandez.escuelaarcade.model.Curso
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
        viewModel.userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        /*En esta parte se obtiene una instancia de NavHostFragment que será inicializada
       a partir del fragmentContainer de esta actividad. La idea es que se pueda tener acceso
       a su navController*/
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        /* Una vez teniendo el navController, se configura que la barra de navegación tenga sus
        * datos a partir de cada fragmento al que se navege.*/
        setupActionBarWithNavController(this,navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}