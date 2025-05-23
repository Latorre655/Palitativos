package co.unab.johanyabi.proyectopalitativos

// Importaciones para Jetpack Compose (más limpias que el código anterior)
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage

/**
 * PANTALLA PARA MOSTRAR LA ESCALA ECOG
 * ECOG (Eastern Cooperative Oncology Group) es una escala médica
 * que evalúa el estado funcional de pacientes con cáncer
 */
@Preview
@Composable
fun Ecog(
    // Función callback para manejar el botón de retroceso
    onClickBack: () -> Unit = {}
){
    /**
     * SCAFFOLD - ESTRUCTURA PRINCIPAL
     * Mismo patrón que ChildPugh pero para contenido médico diferente
     */
    Scaffold(
        // BARRA SUPERIOR PERSONALIZADA
        topBar = {
            /**
             * CONTENEDOR HORIZONTAL PARA LA TOPBAR
             * Organiza: [Ícono Volver] [Título Centrado] [Espacio vacío]
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho de la pantalla
                    .background(Color(0xFF362F5E)) // Mismo color corporativo morado
                    .padding(top = 35.dp) // Compensación para barra de estado del sistema
                    .padding(horizontal = 12.dp, vertical = 16.dp), // Espaciado interno
                verticalAlignment = Alignment.CenterVertically // Alinea elementos al centro vertical
            ) {
                /**
                 * BOTÓN DE NAVEGACIÓN HACIA ATRÁS
                 * Componente reutilizable para volver a la pantalla anterior
                 */
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ícono Material Design estándar
                    contentDescription = "Volver", // Texto para lectores de pantalla (accesibilidad)
                    tint = Color.White, // Color del ícono (blanco sobre fondo morado)
                    modifier = Modifier
                        .clickable { // Modificador que hace el elemento clickeable
                            onClickBack() // Ejecuta la función de navegación
                        }
                )

                /**
                 * TÍTULO DE LA ESCALA MÉDICA
                 * "ESCALA ECOG" - Diferente al Child Pugh Score
                 */
                Text(
                    text = "ESCALA ECOG", // DIFERENCIA PRINCIPAL: Texto específico para ECOG
                    color = Color.White,
                    fontSize = 18.sp, // Tamaño consistente con otras pantallas
                    modifier = Modifier.weight(1f), // Expande para ocupar espacio restante
                    textAlign = TextAlign.Center, // Centra el texto horizontalmente
                    fontWeight = FontWeight.Bold // Negrita para dar importancia
                )
            }
        },
        // COLOR DE FONDO CONSISTENTE CON LA APLICACIÓN
        containerColor = Color(0xFFE9F1F9) // Azul claro suave, mismo que ChildPugh
    ) { innerPadding ->
        /**
         * CONTENIDO PRINCIPAL - COLUMNA VERTICAL
         * Organiza el contenido de arriba hacia abajo
         */
        Column(
            modifier = Modifier
                .padding(innerPadding) // IMPORTANTE: Respeta el spacing del Scaffold
                .fillMaxSize() // Ocupa todo el espacio disponible
                .padding(top = 150.dp) // Gran espacio superior para centrar visualmente
                .padding(horizontal = 16.dp, vertical = 8.dp), // Márgenes laterales
            horizontalAlignment = Alignment.CenterHorizontally // Centra todo horizontalmente
        ) {
            /**
             * COMPONENTE PRINCIPAL: IMAGEN DE LA ESCALA ECOG
             * AsyncImage maneja la carga asíncrona desde internet
             */
            AsyncImage(
                // URL ESPECÍFICA PARA LA ESCALA ECOG (diferente imagen que ChildPugh)
                model = "https://www.revistaeldolor.cl/storage/fotos/images/image%2814%29.png",
                contentDescription = "Tabla Child Pugh", // TODO: Corregir a "Escala ECOG"

                modifier = Modifier
                    .fillMaxWidth() // La imagen ocupa todo el ancho disponible
                    .padding(top = 16.dp), // Pequeño espacio superior

                // ESCALA DE CONTENIDO: Ajusta imagen al ancho manteniendo proporciones
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
