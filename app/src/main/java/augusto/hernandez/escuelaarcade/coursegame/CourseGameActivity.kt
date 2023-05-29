package augusto.hernandez.escuelaarcade.coursegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import augusto.hernandez.escuelaarcade.databinding.ActivityCoursegameBinding
import augusto.hernandez.escuelaarcade.model.Curso

class CourseGameActivity:AppCompatActivity() {
    lateinit var binding: ActivityCoursegameBinding
    lateinit var curso: Curso
    private lateinit var viewModel:CourseGameViewModel
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
        //TODO hay que configurar los datos que serán visibles para este fragment
    }
}