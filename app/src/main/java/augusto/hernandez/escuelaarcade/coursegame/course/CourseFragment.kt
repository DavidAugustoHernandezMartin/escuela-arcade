package augusto.hernandez.escuelaarcade.coursegame.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import augusto.hernandez.escuelaarcade.coursegame.CourseGameViewModel
import augusto.hernandez.escuelaarcade.coursegame.course.CourseListFragment.Companion.LECCION
import augusto.hernandez.escuelaarcade.databinding.CourseFragmentBinding
import augusto.hernandez.escuelaarcade.model.Leccion

class CourseFragment:Fragment() {
    private lateinit var binding:CourseFragmentBinding
    private val viewModel: CourseGameViewModel by activityViewModels()
    private var leccion: Leccion? = null
    private var numero:Int = 0

    companion object{
        const val NUMERO = "NUMERO"
    }

    /*Es importante resaltar que aquí solo se definirá la creación
    * del objeto fragment, no su renderización*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leccion = it.getParcelable(LECCION)
            numero = it.getInt(CourseListFragment.NUMERO)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val fragmentBinding = CourseFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = viewLifecycleOwner
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setLeccion(leccion!!)
        binding.apply {
            dataModel = viewModel
            imageUrl = viewModel.leccionActual.value?.imagenes
            app = this@CourseFragment
        }
    }

    fun goToGame(){
        val action: NavDirections = CourseFragmentDirections.actionCourseFragmentToGameFragment(numero)
        Log.d("ARGUMENTOS","Se está enviando el argumento $numero a GameFragment")
        findNavController().navigate(action)
    }
}