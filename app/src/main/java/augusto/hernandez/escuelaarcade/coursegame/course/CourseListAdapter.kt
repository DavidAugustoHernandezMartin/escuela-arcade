package augusto.hernandez.escuelaarcade.coursegame.course

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.model.Contenido
import com.google.android.material.card.MaterialCardView

class CourseListAdapter (private val context:Context,private val contenido:Contenido?):RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
    class CourseListViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val card: MaterialCardView = view.findViewById(R.id.lessonCard)
        val name: TextView = view.findViewById(R.id.lessonName)
        val number:TextView = view.findViewById(R.id.lessonNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_lesson_card_layout, parent, false)
        return CourseListViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return contenido?.lecciones?.size ?: 0
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val leccion = contenido?.lecciones?.get(position)
        holder.name.text = leccion?.nombre
        holder.number.text = context.getString(R.string.numero_de_lecciones,contenido?.lecciones?.size)
        holder.card.setOnClickListener{
            val action:NavDirections = CourseListFragmentDirections.actionCourseListFragmentToCourseFragment(leccion)
            it.findNavController().navigate(action)
        }
    }
}