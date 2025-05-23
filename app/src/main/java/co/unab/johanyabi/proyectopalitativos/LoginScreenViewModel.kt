package co.unab.johanyabi.proyectopalitativos

import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

/**
 * VIEWMODEL PARA LA PANTALLA DE LOGIN
 *
 * ViewModel es parte de la arquitectura MVVM (Model-View-ViewModel):
 * - Mantiene el estado de la UI
 * - Sobrevive a cambios de configuración (rotación de pantalla)
 * - Maneja la lógica de negocio separada de la UI
 */
class LoginScreenViewModel : ViewModel() {

    //INSTANCIA DE FIREBASE AUTHENTICATION
    // Firebase.auth es el punto de entrada para todas las operaciones de autenticación
    private val auth: FirebaseAuth = Firebase.auth

    //ESTADO DE CARGA (LOADING)
    // MutableLiveData permite observar cambios desde la UI
    // private val _loading = variable interna (encapsulación)
    private val _loading = MutableLiveData(false)

    /**
     * FUNCIÓN PRINCIPAL: LOGIN CON CREDENCIALES DE GOOGLE
     *
     * @param credential: Las credenciales obtenidas del Google Sign-In
     * @param home: Función lambda que se ejecuta cuando el login es exitoso
     */
    @OptIn(UnstableApi::class) // Anotación necesaria para usar Log de Media3
    fun singInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
    //LANZAR CORRUTINA EN EL SCOPE DEL VIEWMODEL
        // viewModelScope.launch garantiza que la corrutina se cancele cuando el ViewModel se destruya
        viewModelScope.launch {
            try {
                //INICIAR PROCESO DE AUTENTICACIÓN CON FIREBASE
                auth.signInWithCredential(credential)
                    //CALLBACK PARA MANEJAR RESULTADO EXITOSO
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //LOGIN EXITOSO
                            Log.d("LoginViewModel", "Logueado con google exitoso")
                            // Ejecutar la función de navegación al home
                            home()
                        } else {
                            //TASK COMPLETADO PERO CON ERROR
                            Log.d("LoginViewModel", "Task completado con error: ${task.exception?.message}")
                        }
                    }
                    //CALLBACK PARA MANEJAR FALLOS DE AUTENTICACIÓN
                    .addOnFailureListener { exception ->
                        Log.d("LoginViewModel", "Logueado con google fallido: ${exception.message}")
                    }
            } catch (ex: Exception) {
                //MANEJO DE EXCEPCIONES GENERALES
                // Captura cualquier excepción que no sea específica de Firebase
                Log.d("LoginViewModel", "Excepción general: ${ex.localizedMessage}")
            }
        }
}