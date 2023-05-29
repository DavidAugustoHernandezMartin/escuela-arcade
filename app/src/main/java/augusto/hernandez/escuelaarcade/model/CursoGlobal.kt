package augusto.hernandez.escuelaarcade.model

import androidx.annotation.DrawableRes

data class CursoGlobal(val id:String,
                       val nombreDeCurso:String,
                       val numeroDeLecciones:Int,
                       val descripcion:String,
                       @DrawableRes val imagen:Int)
