package augusto.hernandez.escuelaarcade.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Contenido(val id:String?,val lecciones:Array<Leccion>?){
    constructor():this(null,null)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contenido

        if (id != other.id) return false
        if (lecciones != null) {
            if (other.lecciones == null) return false
            if (!lecciones.contentEquals(other.lecciones)) return false
        } else if (other.lecciones != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (lecciones?.contentHashCode() ?: 0)
        return result
    }
}
@Parcelize
data class Leccion (val  nombre:String="",
                    val texto:Array<String> = arrayOf(),
                    val imagenes:Array<String> = arrayOf(),
                    val juego:HashMap<String,Array<String>?>):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Leccion

        if (nombre != other.nombre) return false
        if (!texto.contentEquals(other.texto)) return false
        if (!imagenes.contentEquals(other.imagenes)) return false
        if (juego != other.juego) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nombre.hashCode()
        result = 31 * result + texto.contentHashCode()
        result = 31 * result + imagenes.contentHashCode()
        result = 31 * result + juego.hashCode()
        return result
    }

    override fun toString(): String {
        return "Leccion(nombre='$nombre', texto=${texto.contentToString()}, imagenes=${imagenes.contentToString()}, juego=$juego)"
    }
}
