package augusto.hernandez.escuelaarcade.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class Curso(
    val id: String = "",
    var nombre: String = "",
    val progreso:Array<Int> = arrayOf(
        0,0
    ),
    var maxima_puntuacion: Long = 0,
    var ultima_leccion:String = ""
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Curso

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (!progreso.contentEquals(other.progreso)) return false
        if (maxima_puntuacion != other.maxima_puntuacion) return false
        if (ultima_leccion != other.ultima_leccion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + progreso.contentHashCode()
        result = 31 * result + maxima_puntuacion.hashCode()
        result = 31 * result + ultima_leccion.hashCode()
        return result
    }

    override fun toString(): String {
        return "Curso(id='$id', nombre='$nombre', progreso=${progreso.contentToString()}, maxima_puntuacion=$maxima_puntuacion, ultima_leccion='$ultima_leccion')"
    }
}
