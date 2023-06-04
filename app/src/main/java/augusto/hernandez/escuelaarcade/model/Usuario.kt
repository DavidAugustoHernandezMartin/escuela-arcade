package augusto.hernandez.escuelaarcade.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*Esta clase de datos representa el documento que se estructurará y guardará en Firestore.
* Tiene subclases que facilitan la organización de entidades correspondientes al esquema de
* la aplicación.*/
data class Usuario(
    val id:String?,
    val perfil:Perfil?,
    val registros:MutableList<Curso>?
){
    constructor() : this(null,null,null)

    override fun toString(): String {
        return "Usuario(id=$id, perfil=$perfil, registros=$registros)"
    }
}
data class Perfil(
    val cursos: MutableList<String> = mutableListOf(),
    var es_autor: Boolean = false,
    var foto_de_fondo: String = "",
    var foto_de_perfil: String = "",
    var lecciones: Long = 0,
    var nombre: String = "",
    var puntuacion_maxima: Long = 0
) {
    override fun toString(): String {
        return "Perfil(cursos=$cursos, es_autor=$es_autor, foto_de_fondo='$foto_de_fondo', foto_de_perfil='$foto_de_perfil', lecciones=$lecciones, nombre='$nombre', puntuacion_maxima=$puntuacion_maxima)"
    }
}

/*La utilización de calses Parcelable permitirá la comunicación de estados sin la necesidad
* de sincronización remota.*/
@Parcelize
data class Curso(
    val id: String = "",
    var nombre: String = "",
    val progreso:List<Int> = listOf(
        0,0
    ),
    var maxima_puntuacion: Long = 0,
    var ultima_leccion:String = ""
) : Parcelable {
    override fun toString(): String {
        return "Curso(id='$id', nombre='$nombre', progreso=$progreso, maxima_puntuacion=$maxima_puntuacion, ultima_leccion='$ultima_leccion')"
    }
}
