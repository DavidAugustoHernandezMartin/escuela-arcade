package augusto.hernandez.escuelaarcade.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.databinding.HomeFragmentBinding
import augusto.hernandez.escuelaarcade.model.AppViewModel
import augusto.hernandez.escuelaarcade.model.HomeViewModel
import augusto.hernandez.escuelaarcade.model.states.Resource
import com.google.android.material.snackbar.Snackbar

class HomeFragment:Fragment() {
    //Se inicia el viewmodel compartido
    private val viewModel: HomeViewModel by activityViewModels()
    private val appViewModel:AppViewModel by activityViewModels()
    // Binding object instance corresponding to the fragment_u8.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.lifecycleOwner = this
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            dataModel = viewModel
        }
        appViewModel.user.observe(viewLifecycleOwner){ user ->
            user?.let {
                binding.apply {
                    recyclerviewHome.adapter = HomeListAdapter(requireContext(), it)
                }
            }
        }
        appViewModel.loginStatus.observe(viewLifecycleOwner) { loginResource ->
            when (loginResource) {
                is Resource.Loading -> {
                    binding.apply {
                        loadingHome.visibility = View.VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        loadingHome.visibility = View.GONE
                        homeLogo.visibility = View.VISIBLE
                        recyclerviewHome.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        loadingHome.setImageResource(R.drawable.ic_connection_error)
                    }
                    Snackbar.make(binding.root,R.string.no_connection,Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}