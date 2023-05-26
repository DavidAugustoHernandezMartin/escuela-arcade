package augusto.hernandez.escuelaarcade.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import augusto.hernandez.escuelaarcade.databinding.StatisticsFragmentBinding
import augusto.hernandez.escuelaarcade.model.AppViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class StatisticsFragment:Fragment() {

    //Se inicia el viewmodel compartido
    private val viewModel: AppViewModel by activityViewModels()

    // Binding object instance corresponding to the statistics_fragment.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private lateinit var binding: StatisticsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticsFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el ViewPager y el TabLayout
        val viewPager: ViewPager2 = binding.pager
        val tabs: TabLayout = binding.tabLayout

        // Observar los cambios en las categorías
        viewModel.courses.observe(viewLifecycleOwner) { categories ->
            if (!categories.isNullOrEmpty()) {
                    // Cuando las categorías se actualizan, configura el adaptador de ViewPager
                    viewPager.adapter = StatisticsAdapter(this, categories)
                    // Configurar TabLayoutMediator para conectar el TabLayout y el ViewPager
                    TabLayoutMediator(tabs, viewPager) { tab, position ->
                        tab.text = categories[position].nombre
                    }.attach()
                // Esto es necesario para que TabLayout muestre las pestañas correctamente
                binding.noStats.visibility = View.GONE
            } else {
                // Aquí puedes configurar y mostrar una vista por defecto si categories está vacío
                // Por ejemplo, podrías tener un TextView en tu layout que normalmente está oculto,
                // y aquí podrías hacerlo visible y establecer su texto a un mensaje de error.
                binding.noStats.visibility = View.VISIBLE
            }
        }

        // Obtener las categorías al inicio
        viewModel.fetchCategories()
    }

}