package augusto.hernandez.escuelaarcade.coursegame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import augusto.hernandez.escuelaarcade.model.Contenido
import augusto.hernandez.escuelaarcade.model.Curso
import augusto.hernandez.escuelaarcade.model.Leccion
import augusto.hernandez.escuelaarcade.model.states.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

const val COURSEDATA = "COURSEDATA"
class CourseGameViewModel: ViewModel() {
     val curso:MutableLiveData<Curso> = MutableLiveData()
     val contenido:MutableLiveData<Contenido?> = MutableLiveData()
     private val _leccionActual:MutableLiveData<Leccion> = MutableLiveData()
     val leccionActual:LiveData<Leccion> = _leccionActual

     private val _fetchStatus = MutableLiveData<Resource<Contenido>>()
     val fetchStatus:LiveData<Resource<Contenido>> get()= _fetchStatus

     private val database = Firebase.firestore
     lateinit var userID:String

     fun setLeccion(leccion: Leccion){
          _leccionActual.value = leccion
     }

     @OptIn(ExperimentalCoroutinesApi::class)
      suspend fun fetchContent(): Contenido? {
          return withContext(Dispatchers.IO) {
               suspendCancellableCoroutine { continuation ->
                    curso.value?.id?.let { c ->
                         database.collection("contenidos")
                              .document(c)
                              .get()
                              .addOnSuccessListener { doc ->
                                   val returnedContent = doc.toObject<Contenido>()
                                   Log.d(COURSEDATA,"datos de contenido obtenidos de returnedContent: ${returnedContent.toString()}")
                                   continuation.resume(returnedContent) {
                                        it.printStackTrace()
                                   }
                              }
                              .addOnFailureListener { e ->
                                   Log.e(COURSEDATA, "Ocurrió un error al sincronizar datos de contenido con Firebase: ${e.message}")
                                   continuation.resume(null) {
                                        it.printStackTrace()
                                   }
                              }
                    }
               }
          }
     }

     fun fetch() {
          viewModelScope.launch {
               _fetchStatus.value = Resource.Loading()
               try {
                    val returnedContent = fetchContent()
                    if (returnedContent != null) {
                         _fetchStatus.value = Resource.Success(returnedContent)
                         contenido.value = returnedContent
                    } else {
                         _fetchStatus.value = Resource.Error("No se pudieron obtener contenidos del curso")
                         Log.e(COURSEDATA, "La solicitud de contenido del curso ${curso.value?.nombre} devolvió nulo.")
                    }
               } catch (e: Exception) {
                    _fetchStatus.value = Resource.Error("Error de obtención de datos de curso")
                    Log.e(COURSEDATA, "Ocurrió un error de solicitud de contenidos de curso: ${e.message}")
               }
          }
     }
}