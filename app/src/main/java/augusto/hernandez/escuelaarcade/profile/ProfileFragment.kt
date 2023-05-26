package augusto.hernandez.escuelaarcade.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import augusto.hernandez.escuelaarcade.databinding.HomeFragmentBinding
import augusto.hernandez.escuelaarcade.databinding.ProfileFragmentBinding
import augusto.hernandez.escuelaarcade.model.AppViewModel

class ProfileFragment:Fragment() {
    //Se inicia el viewmodel compartido
    private val viewModel: AppViewModel by activityViewModels()

    // Binding object instance corresponding to the fragment_u8.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private lateinit var binding: ProfileFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = this
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dataModel = viewModel
        }
    }
}