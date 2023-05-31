package augusto.hernandez.escuelaarcade.coursegame.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import augusto.hernandez.escuelaarcade.coursegame.CourseGameViewModel
import augusto.hernandez.escuelaarcade.databinding.CourseListFragmentBinding

class CourseListFragment: Fragment() {
    private lateinit var binding: CourseListFragmentBinding
    private lateinit var viewModel: CourseGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = CourseListFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = this
        return fragmentBinding.root
    }
    //TODO terminar la gestión del valor nulo para el adaptador y terminar de ensamblar el resto de elementos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dataModel = viewModel
        }
    }
}