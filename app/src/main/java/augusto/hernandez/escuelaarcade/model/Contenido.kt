package augusto.hernandez.escuelaarcade.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*Este es un modelo de datos utilizado para obtener un contenido de un curso en la base de datos de
* Firestore. Al igual que con la clase Usuario, ésta tiene una subclase Parcelable que permitirá
* comunicar el estado del contenido especificado a otros componentes sin necesidad de lanzar
* procesos de sincronización de datos.*/
data class Contenido(val id:String?,val lecciones:List<Leccion>?){
    constructor():this(null,null)

    override fun toString(): String {
        return "Contenido(id=$id, lecciones=$lecciones)"
    }

}
@Parcelize
data class Leccion (val  nombre:String="",
                    val texto:List<String> = listOf(),
                    val imagenes:List<String> = listOf(),
                    val juego:HashMap<String,List<String>> = hashMapOf("preguntas" to listOf(),
                                                                         "respuestas" to listOf()
                    )
):Parcelable{
    override fun toString(): String {
        return "Leccion(nombre='$nombre', texto=$texto, imagenes=$imagenes, juego=$juego)"
    }

}
