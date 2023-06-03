package augusto.hernandez.escuelaarcade.coursegame.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import augusto.hernandez.escuelaarcade.coursegame.CourseGameViewModel
import augusto.hernandez.escuelaarcade.databinding.CourseListFragmentBinding
import augusto.hernandez.escuelaarcade.model.states.PlaceholderAdapter

class CourseListFragment: Fragment() {
    private lateinit var binding: CourseListFragmentBinding
    private val viewModel: CourseGameViewModel by activityViewModels()

    companion object{
        const val LECCION = "LECCION"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = CourseListFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = viewLifecycleOwner
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dataModel = viewModel
        }
        viewModel.contenido.observe(viewLifecycleOwner){ user ->
            user?.let {
                binding.apply {
                    recyclerviewListLessons.adapter = CourseListAdapter(requireContext(), it)
                }
            }?: run {
                binding.apply {
                    recyclerviewListLessons.adapter = PlaceholderAdapter()
                }
            }
        }
    }
}