package co.unab.johanyabi.proyectopalitativos

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore

class PacienteViewModel : ViewModel() {
    private val _pacientes = mutableStateListOf<Paciente>()
    val pacientes: List<Paciente> = _pacientes

    init {
        obtenerPacientesDesdeFirebase()
    }

    private fun obtenerPacientesDesdeFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("pacientes").addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener

            _pacientes.clear()
            for (document in snapshot.documents) {
                val paciente = document.toObject(Paciente::class.java)
                if (paciente != null) {
                    _pacientes.add(paciente)
                }
            }
        }
    }
}