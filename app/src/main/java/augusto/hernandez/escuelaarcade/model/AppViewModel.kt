package augusto.hernandez.escuelaarcade.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import augusto.hernandez.escuelaarcade.home.HomeFragment
import augusto.hernandez.escuelaarcade.model.states.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

const val LOGINDATA = "LOGINDATA"

class AppViewModel : ViewModel() {

    // Datos de MutableLiveData de la aplicación
    private val _courses = MutableLiveData<MutableList<Curso>?>()
    private val _loginStatus = MutableLiveData<Resource<Usuario>>()


    //Datos LiveData públicos
    val courses:LiveData<MutableList<Curso>?> get()= _courses
    val loginStatus:LiveData<Resource<Usuario>> get()= _loginStatus
    //Datos de Firebase
    private val _user = MutableLiveData<Usuario?>()
    val user:LiveData<Usuario?> get()= _user
    private val database:FirebaseFirestore = Firebase.firestore
    private val auth:FirebaseUser= FirebaseAuth.getInstance().currentUser!!




    fun fetchCategories() {
        // Este es solo un ejemplo, debes implementar tu propia lógica de red aquí
        val fetchedCategories = _user.value?.registros
        _courses.value = fetchedCategories
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun loginUser(): Usuario? {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { continuation ->
                database.collection("usuarios")
                    .document(auth.uid)
                    .get()
                    .addOnSuccessListener { doc ->
                         val returnedUser = doc.toObject<Usuario>()
                        Log.d(LOGINDATA,"datos de usuario obtenidos de returnedUser: ${returnedUser.toString()}")
                        continuation.resume(returnedUser) {
                            it.printStackTrace()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(LOGINDATA, "Ocurrió un error al sincronizar datos le login con Firebase: ${e.message}")
                        continuation.resume(null) {
                            it.printStackTrace()
                        }
                    }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun initUser(usuario: Usuario) {
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine{ continuation ->
                database.collection("usuarios")
                    .document(auth.uid)
                    .set(usuario)
                    .addOnSuccessListener {
                        Log.d(LOGINDATA, "Usuario ${usuario.perfil?.nombre} sincronizado")
                        continuation.resume(Unit) {
                            it.printStackTrace()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(LOGINDATA, "No se pudo sincronizar el usuario ${usuario.perfil?.nombre} ", e)
                        continuation.cancel(e)
                    }
            }
        }
    }


    fun login() {
        viewModelScope.launch {
            _loginStatus.value = Resource.Loading()
            try {
                val returnedUser = loginUser()
                if (returnedUser != null) {
                    _loginStatus.value = Resource.Success(returnedUser)
                    _user.value = returnedUser
                } else {
                    val newUser = customUser()
                    initUser(newUser)
                    _loginStatus.value = Resource.Success(newUser)
                    _user.value = newUser
                }
            } catch (e: Exception) {
                _loginStatus.value = Resource.Error("Error de inicio de sesión")
                Log.e(LOGINDATA, "Ocurrió un error de login: ${e.message}")
            }
        }
    }


    private fun customUser(): Usuario {
        val id: String = auth.uid
        val name: String = if (auth.displayName != null) auth.displayName!! else "usuario"
        val profilePic: String = if (auth.photoUrl != null) auth.photoUrl.toString() else "foto_de_perfil"

        return Usuario(
            id,
            Perfil(
                mutableListOf(),
                false,
                "foto_de_fondo",
                profilePic,
                0,
                name,
                0
            ),
            mutableListOf()
        )
    }
}