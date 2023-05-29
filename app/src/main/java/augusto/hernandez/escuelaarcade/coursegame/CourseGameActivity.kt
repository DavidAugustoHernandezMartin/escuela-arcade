package augusto.hernandez.escuelaarcade.coursegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import augusto.hernandez.escuelaarcade.databinding.ActivityCoursegameBinding
import augusto.hernandez.escuelaarcade.model.Curso

class CourseGameActivity:AppCompatActivity() {
    lateinit var binding: ActivityCoursegameBinding
    lateinit var curso: Curso
    companion object{
        const val CURSO = "CURSO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursegameBinding.inflate(layoutInflater)

        curso = intent.getParcelableExtra<Curso>(CURSO) as Curso
    }
}