package augusto.hernandez.escuelaarcade.coursegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import augusto.hernandez.escuelaarcade.model.Contenido
import augusto.hernandez.escuelaarcade.model.Curso
import augusto.hernandez.escuelaarcade.model.Leccion

class CourseGameViewModel: ViewModel() {
     val curso:MutableLiveData<Curso> = MutableLiveData()
     val contenido:MutableLiveData<Contenido> = MutableLiveData()
     private val _leccionActual:MutableLiveData<Leccion> = MutableLiveData()
     val leccionActual:LiveData<Leccion> = _leccionActual

     fun setLeccion(leccion: Leccion){
          _leccionActual.value = leccion
     }
}