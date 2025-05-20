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

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    @OptIn(UnstableApi::class)
    fun singInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("", "Logueado con google exitoso")
                            home()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("", "Logueado con google fallido")
                    }
            } catch (ex: Exception) {
                Log.d("", "Excepción ${ex.localizedMessage}")
            }
        }
}