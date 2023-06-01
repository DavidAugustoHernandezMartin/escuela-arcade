package augusto.hernandez.escuelaarcade.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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
