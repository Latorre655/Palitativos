package co.unab.johanyabi.proyectopalitativos

// Importaciones necesarias para Jetpack Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // Biblioteca para cargar imágenes desde URL

/**
 * FUNCIÓN COMPOSABLE PRINCIPAL
 * @Preview permite ver la pantalla en el diseñador de Android Studio
 * @Composable marca que esta función puede ser parte de la UI de Compose
 */
@Preview
@Composable
fun ChildPugh(
    // Parámetro de función lambda para manejar el botón de volver
    onClickBack: () -> Unit = {},
) {
    /**
     * SCAFFOLD - ESTRUCTURA BASE DE LA PANTALLA
     * Scaffold es como el "esqueleto" de tu pantalla, proporciona:
     * - TopBar: Barra superior
     * - Content: Contenido principal
     * - Otros elementos como BottomBar, FloatingActionButton, etc.
     */
    Scaffold(
        // CONFIGURACIÓN DE LA BARRA SUPERIOR (TopBar)
        topBar = {
            /**
             * ROW PARA LA BARRA SUPERIOR
             * Row organiza elementos horizontalmente (uno al lado del otro)
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho disponible
                    .background(Color(0xFF362F5E)) // Color de fondo morado oscuro
                    .padding(top = 35.dp) // Padding superior para evitar la barra de estado
                    .padding(horizontal = 12.dp, vertical = 16.dp), // Padding interno
                verticalAlignment = Alignment.CenterVertically // Centra verticalmente los elementos
            ) {
                /**
                 * ÍCONO DE FLECHA HACIA ATRÁS
                 * Permite al usuario regresar a la pantalla anterior
                 */
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ícono predefinido de Material Design
                    contentDescription = "Volver", // Descripción para accesibilidad
                    tint = Color.White, // Color del ícono
                    modifier = Modifier
                        .clickable { // Hace el ícono clickeable
                            onClickBack() // Ejecuta la función cuando se hace clic
                        }
                )

                /**
                 * TÍTULO DE LA PANTALLA
                 * Muestra "CHILD PUGH SCORE" centrado en la barra superior
                 */
                Text(
                    text = "CHILD PUGH SCORE",
                    color = Color.White,
                    fontSize = 18.sp, // Tamaño de fuente en "scaled pixels"
                    modifier = Modifier.weight(1f), // Ocupa todo el espacio restante
                    textAlign = TextAlign.Center, // Centra el texto horizontalmente
                    fontWeight = FontWeight.Bold // Texto en negrita
                )
            }
        },
        // COLOR DE FONDO DE TODA LA PANTALLA
        containerColor = Color(0xFFE9F1F9) // Azul claro suave
    ) { innerPadding ->
        /**
         * CONTENIDO PRINCIPAL DE LA PANTALLA
         * Column organiza elementos verticalmente (uno encima del otro)
         */
        Column(
            modifier = Modifier
                .padding(innerPadding) // Respeta el padding del Scaffold
                .fillMaxSize() // Ocupa todo el tamaño disponible
                .padding(top = 150.dp) // Espacio desde la parte superior
                .padding(horizontal = 16.dp, vertical = 8.dp), // Padding lateral y vertical
            horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente el contenido
        ) {
            /**
             * IMAGEN ASÍNCRONA - COMPONENTE MÁS IMPORTANTE
             * AsyncImage de la biblioteca Coil permite cargar imágenes desde internet
             * de forma asíncrona (sin bloquear la UI)
             */
            AsyncImage(
                // URL de la imagen de la tabla Child Pugh Score
                model = "https://www.2minutemedicine.com/wp-content/uploads/2013/07/Child-Pugh-Score-Liver-Transplant-Cirrhosis-2-Minute-Medicine.png",
                contentDescription = "Tabla Child Pugh", // Descripción para accesibilidad
                modifier = Modifier
                    .fillMaxWidth() // La imagen ocupa todo el ancho disponible
                    .padding(top = 16.dp), // Espacio superior
                contentScale = ContentScale.FillWidth // Escala la imagen para llenar el ancho
            )
        }
    }
}