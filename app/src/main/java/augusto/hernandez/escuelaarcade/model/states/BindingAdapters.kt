package augusto.hernandez.escuelaarcade.model.states

/*Estas definiciones de BindingAdapter permiten personalizar un proceso a realizar
* sobre los datos que se asignan como valores de atributos en las vistas.
* Cada Binding es una operación automática que se ejecutará sobre el valor
* provisto en el atributo personalizado para reducir código de implementación en
* otras clases o componentes del programa.*/

/*@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,status:MarsApiStatus?){
    when(status){
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        null ->{
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
    }
}*/