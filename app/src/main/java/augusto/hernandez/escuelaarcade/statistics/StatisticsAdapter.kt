package augusto.hernandez.escuelaarcade.statistics

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import augusto.hernandez.escuelaarcade.model.Curso

class StatisticsAdapter(f: Fragment, private val statistics: MutableList<Curso>) :
    FragmentStateAdapter(f) {
    override fun getItemCount(): Int {
       return statistics.size
    }

    override fun createFragment(position: Int): Fragment {
        val category = statistics[position]
        return StatsTab.newInstance(category.nombre,category)
    }

}