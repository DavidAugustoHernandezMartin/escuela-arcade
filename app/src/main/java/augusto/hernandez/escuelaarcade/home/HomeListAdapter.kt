package augusto.hernandez.escuelaarcade.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.model.CursoGlobal
import augusto.hernandez.escuelaarcade.model.HomeViewModel
import augusto.hernandez.escuelaarcade.model.Usuario
import augusto.hernandez.escuelaarcade.model.data.CourseData


class HomeListAdapter(private val context: Context,private val user:Usuario): RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder>() {
    private val courseList:Array<CursoGlobal> = CourseData.listaDeCursos
    class HomeListViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.courseImage)
        val title:TextView = view.findViewById(R.id.courseTitle)
        val lessons:TextView = view.findViewById(R.id.courseLessons)
        val description:TextView = view.findViewById(R.id.courseDescription)
        val card = view
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.global_course_card_layout, parent, false)
        return HomeListViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        val item =  courseList[position]
        holder.title.text = item.nombreDeCurso
        holder.description.text = item.descripcion
        holder.lessons.text = context.getString(R.string.numero_de_lecciones,item.numeroDeLecciones)
        holder.image.setImageResource(item.imagen)
        holder.card.setOnClickListener{
            HomeViewModel.goToCourse(context,user,item.id,item.nombreDeCurso,item.numeroDeLecciones)
        }
    }

}