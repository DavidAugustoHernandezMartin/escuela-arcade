package augusto.hernandez.escuelaarcade.model

data class Contenido(val id:String?,
                     val lecciones:Array<Array<String>>?,
                     val imagenes:Array<Array<String>>?,
                     val pasos_de_imagen:Array<Array<Int>>?,
                     val links:Array<Link>?){
    constructor():this(null,null,null,null,null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contenido

        if (id != other.id) return false
        if (lecciones != null) {
            if (other.lecciones == null) return false
            if (!lecciones.contentDeepEquals(other.lecciones)) return false
        } else if (other.lecciones != null) return false
        if (imagenes != null) {
            if (other.imagenes == null) return false
            if (!imagenes.contentDeepEquals(other.imagenes)) return false
        } else if (other.imagenes != null) return false
        if (pasos_de_imagen != null) {
            if (other.pasos_de_imagen == null) return false
            if (!pasos_de_imagen.contentDeepEquals(other.pasos_de_imagen)) return false
        } else if (other.pasos_de_imagen != null) return false
        if (links != null) {
            if (other.links == null) return false
            if (!links.contentEquals(other.links)) return false
        } else if (other.links != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (lecciones?.contentDeepHashCode() ?: 0)
        result = 31 * result + (imagenes?.contentDeepHashCode() ?: 0)
        result = 31 * result + (pasos_de_imagen?.contentDeepHashCode() ?: 0)
        result = 31 * result + (links?.contentHashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Contenido(id=$id, lecciones=${lecciones?.contentToString()}, imagenes=${imagenes?.contentToString()}, pasos_de_imagen=${pasos_de_imagen?.contentToString()}, links=${links?.contentToString()})"
    }

}
data class Link(val descipcion:String="",val url:String=""){
    override fun toString(): String {
        return "Link(descipcion='$descipcion', url='$url')"
    }
}