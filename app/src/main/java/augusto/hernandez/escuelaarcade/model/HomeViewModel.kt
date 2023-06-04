package augusto.hernandez.escuelaarcade.model

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import augusto.hernandez.escuelaarcade.coursegame.CourseGameActivity

class HomeViewModel:ViewModel() {

    companion object {
        fun goToCourse(context: Context, user:Usuario, courseId:String,courseName:String,courseLength:Int) {
            //TODO cambiar esto para crear un curso correctamente
            val course = when {
                user.registros.isNullOrEmpty() || !user.registros.any{x -> x.id == courseId}-> {
                    Curso(courseId,courseName, listOf(0,courseLength),0L,"")
                }
                else -> {
                    user.registros.first{ c -> c.id == courseId}
                }
            }
                val go = Intent(context, CourseGameActivity::class.java)
                go.putExtra(CourseGameActivity.CURSO, course)
                startActivity(context, go, null)
        }
    }
}