package augusto.hernandez.escuelaarcade.coursegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import augusto.hernandez.escuelaarcade.model.Curso

class CourseGameViewModel: ViewModel() {
     val curso:MutableLiveData<Curso> = MutableLiveData()

}