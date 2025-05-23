package co.unab.johanyabi.proyectopalitativos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CalcioCorregido(
    onClickBack: () -> Unit = {}
) {
    var calcioInput by remember { mutableStateOf("") }
    var albuminaInput by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<Double?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF362F5E))
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
                    text = "CALCEMIA CORREGIDA\nPOR ALBÚMINA",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color(0xFFE9F1F9)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Calcio

                Text(
                    text = "Calcio (mg/dL)",
                    color = Color.Black,
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = calcioInput,
                    onValueChange = { calcioInput = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(15.dp))

                //Albúmina
                Text(
                    text = "Albumina (g/dL)",
                    color = Color.Black,
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = albuminaInput,
                    onValueChange = { albuminaInput = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Botón calcular
                Button(
                    onClick = {

                        //convertir los textos a numeros
                        val calcio = calcioInput.toDoubleOrNull()
                        val albumina = albuminaInput.toDoubleOrNull()

                        //si son validos se aplica la formula
                        if (calcio != null && albumina != null) {
                            resultado = calcio + 0.8 * (4 - albumina)
                        } else {
                            resultado = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF362F5E)),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Calcular", color = Color.White)
                }

                // Resultado
                resultado?.let {
                    Text(

                        //redondea el resultado a 2 decimales
                        text = "Resultado: %.2f".format(it),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp

                    )
                }
            }
        }
    }
}