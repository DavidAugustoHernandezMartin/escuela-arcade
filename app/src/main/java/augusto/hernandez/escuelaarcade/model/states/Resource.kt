package augusto.hernandez.escuelaarcade.model.states

/*Esta clase se utiliza en los viewModel para la asignación de
* valores observables que puedan ser variables en su connotación.
* Sirven para poder gestionar los valores correspondientes a los estados
* de renderización condicional.*/
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}
