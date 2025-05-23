package co.unab.johanyabi.proyectopalitativos

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

data class Paciente(
    val nombre: String = "",
    val edad: Int = 0,
    val descripcion: String = ""
)

fun agregarPaciente(
    paciente: Paciente,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("pacientes")
        .add(paciente)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { onError(it) }
}

@Composable
fun AgregarPacienteScreen(onClickBack: () -> Unit) {
    // Estados para los campos
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Estados para los mensajes de error
    var nombreError by remember { mutableStateOf("") }
    var edadError by remember { mutableStateOf("") }
    var descripcionError by remember { mutableStateOf("") }

    // Estado para errores generales
    var formError by remember { mutableStateOf("") }

    //Popup
    var mostrarPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3EDF7)),
    ) {
        // Encabezado con flecha de navegación
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF362F5E))
                        .height(120.dp)
                        .padding(top = 35.dp)
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                onClickBack()
                            }
                    )

                    Text(
                        text = "AÑADIR PACIENTE NUEVO",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            containerColor = Color(0xFFE9F1F9)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            nombreError = ""  // Limpiar el error cuando el usuario escribe
                        },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = nombreError.isNotEmpty()
                    )

                    if (nombreError.isNotEmpty()) {
                        Text(
                            text = nombreError,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = edad,
                        onValueChange = { newValue ->
                            if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                                edad = newValue
                                edadError = ""  // Limpiar el error cuando el usuario escribe
                            }
                        },
                        label = { Text("Edad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = edadError.isNotEmpty()
                    )

                    if (edadError.isNotEmpty()) {
                        Text(
                            text = edadError,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = {
                            descripcion = it
                            descripcionError = ""  // Limpiar el error cuando el usuario escribe
                        },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = descripcionError.isNotEmpty()
                    )

                    if (descripcionError.isNotEmpty()) {
                        Text(
                            text = descripcionError,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (formError.isNotEmpty()) {
                        Text(
                            text = formError,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(),
                            fontSize = 14.sp
                        )
                    }

                    Button(onClick = {
                        // Validar nombre
                        val isValidName: Boolean = validateName(nombre).first
                        nombreError = validateName(nombre).second

                        // Validar edad
                        val isValidAge: Boolean = validateAge(edad).first
                        edadError = validateAge(edad).second

                        // Validar descripción
                        val isValidDesc: Boolean = validateDescription(descripcion).first
                        descripcionError = validateDescription(descripcion).second

                        // Si todas las validaciones son correctas, agregar paciente
                        if (isValidName && isValidAge && isValidDesc) {
                            val edadInt = edad.toIntOrNull() ?: 0
                            val paciente = Paciente(nombre, edadInt, descripcion)
                            agregarPaciente(
                                paciente,
                                onSuccess = {
                                    Log.d("Firestore", "Paciente agregado correctamente")
                                    mostrarPopup = true
                                },
                                onError = { e ->
                                    Log.e("Firestore", "Error: ${e.message}")
                                    formError = "Error al agregar paciente: ${e.message}"
                                }
                            )
                        }
                    })
                    {
                        Text("Agregar Paciente")
                    }

                    if (mostrarPopup) {
                        AlertDialog(
                            onDismissRequest = { mostrarPopup = false },
                            title = { Text("Éxito") },
                            text = { Text("Paciente agregado correctamente.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    mostrarPopup = false
                                    onClickBack() // Regresar al hacer clic en "Aceptar"
                                })
                                {
                                    Text("Aceptar")
                                }
                            })
                    }
                }
            }
        }
    }
}