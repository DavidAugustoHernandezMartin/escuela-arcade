package augusto.hernandez.escuelaarcade.model.states

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import augusto.hernandez.escuelaarcade.R

/*Este adaptador auxiliar permite gestionar el caso en que el valor de estado recibido
* por un renderizador sea nulo.*/
class PlaceholderAdapter: RecyclerView.Adapter<PlaceholderAdapter.ViewHolder>() {

    // Como es un placeholder, no tenemos ningún dato
    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_placeholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Aquí no necesitamos hacer nada, ya que no tenemos ningún dato para vincular.
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}
