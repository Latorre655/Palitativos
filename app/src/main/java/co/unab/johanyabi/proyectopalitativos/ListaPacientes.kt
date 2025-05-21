package co.unab.johanyabi.proyectopalitativos


import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListaPacientes(
    viewModel: PacienteViewModel = viewModel(),
    onClickBack: () -> Unit = {},
    onClickescala: () -> Unit = {}
) {

    var textoBusqueda by remember { mutableStateOf("") }

    val pacientesFiltrados = viewModel.pacientes.filter {
        it.nombre?.contains(textoBusqueda, ignoreCase = true) == true
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            label = { Text("Buscar paciente") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(pacientesFiltrados, key = { it.nombre }) { paciente ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                onClickescala()
                            }
                    ) {
                        Text(text = paciente.nombre)
                    }
                }
            }
        }
    }
}

