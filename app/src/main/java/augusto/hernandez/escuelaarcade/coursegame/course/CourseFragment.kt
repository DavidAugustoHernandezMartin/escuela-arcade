package augusto.hernandez.escuelaarcade.coursegame.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import augusto.hernandez.escuelaarcade.coursegame.CourseGameViewModel
import augusto.hernandez.escuelaarcade.databinding.CourseFragmentBinding
import augusto.hernandez.escuelaarcade.model.Leccion

class CourseFragment:Fragment() {
    private lateinit var binding:CourseFragmentBinding
    private lateinit var viewModel: CourseGameViewModel
    private var leccion: Leccion? = null

    companion object{
        const val LECCION = "LECCION"
    }

    /*Es importante resaltar que aquí solo se definirá la creación
    * del objeto fragment, no su renderización*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leccion = it.getParcelable(LECCION)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val fragmentBinding = CourseFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = this
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}