package augusto.hernandez.escuelaarcade.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.model.Curso

class StatsTab: Fragment() {
    private lateinit var category: String
    private var course: Curso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY) ?: ""
            course = it.getParcelable(ARG_COURSE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.stats_tab_fragment, container, false)
        val textView: TextView = view.findViewById(R.id.courseTextView)
        textView.text = course?.toString() ?: "Datos no disponibles"
        return view
    }

    companion object {
        private const val ARG_CATEGORY = "category"
        private const val ARG_COURSE = "course"

        fun newInstance(category: String, curso: Curso): StatsTab {
            val fragment = StatsTab()
            val args = Bundle().apply {
                putString(ARG_CATEGORY, category)
                putParcelable(ARG_COURSE, curso)
            }
            fragment.arguments = args
            return fragment
        }
    }
}