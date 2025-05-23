package co.unab.johanyabi.proyectopalitativos

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore

//Un ViewModel es una clase que se encarga de preparar y manejar los datos para la UI.
class PacienteViewModel : ViewModel() {

    //Lista mutable privada que contiene los pacientes
    //Se actualiza automaticamente cuando cambian los datos en Firebase
    private val _pacientes = mutableStateListOf<Paciente>()

    //se puede leer la lista de pacientes mas no cambiarla
    val pacientes: List<Paciente> = _pacientes

    //Se ejecuta automaticamente cuando se crea una instancia del ViewModel
    init {
        obtenerPacientesDesdeFirebase()
    }

    //Establece conexion con la base de datos
    private fun obtenerPacientesDesdeFirebase() {

        //Obtiene una instacia de la base de datos
        val db = FirebaseFirestore.getInstance()

        //Establece un Listener persistente (no solo una consulta)
        //actualizacion en tiempo real
        db.collection("pacientes").addSnapshotListener { snapshot, error ->

            //validacion para prevenir crasheos
            if (error != null || snapshot == null) return@addSnapshotListener

            //Previene datos duplicados
            _pacientes.clear()

            //Itera sobre todos los documentos y los convierte a objetos paciente
            for (document in snapshot.documents) {

                //convierte de json a kotlin
                val paciente = document.toObject(Paciente::class.java)

                //Solo agrega pacientes validos (no null)
                if (paciente != null) {
                    _pacientes.add(paciente)
                }
            }
        }
    }
}