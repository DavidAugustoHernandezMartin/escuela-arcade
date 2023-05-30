package augusto.hernandez.escuelaarcade.model.states

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import augusto.hernandez.escuelaarcade.R
import coil.load

/* Estas definiciones de BindingAdapter permiten personalizar un proceso a realizar
* sobre los datos que se asignan como valores de atributos en las vistas.
* Cada Binding es una operación automática que se ejecutará sobre el valor
* provisto en el atributo personalizado para reducir código de implementación en
* otras clases o componentes del programa.*/


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: Any?){
    when (imgUrl) {
        is String -> {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri){
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
        is Int -> {
            imgView.setImageResource(imgUrl)
        }
        else -> {
            imgView.setImageResource(R.drawable.ic_broken_image)
        }
    }
}
