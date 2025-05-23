package co.unab.johanyabi.proyectopalitativos

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.*
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

@Composable
fun Escanear(onClickBack: () -> Unit = {}) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return //Se asegura de que ese contexto es una Activity
    var scannedImageUri by remember { mutableStateOf<Uri?>(null) } //Guarda la URI de la imagen escaneada, inicial null

    //permite iniciar una actividad externa
    val scannerLauncher =
        rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                //almacenamiento del resultado del escaneo
                val scanResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)

                scanResult?.pages?.firstOrNull()?.let { page ->
                    scannedImageUri = page.imageUri
                }
            }
        }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF362F5E))
                    .height(100.dp)
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
                    text = "ESCANER",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                //Boton para escanear
                Button(onClick = {
                    val options = GmsDocumentScannerOptions.Builder()
                        .setGalleryImportAllowed(true)
                        .setPageLimit(2)
                        .setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
                        .setScannerMode(SCANNER_MODE_FULL)
                        .build()

                    val scanner = GmsDocumentScanning.getClient(options)

                    //lanzar el escaner
                    scanner.getStartScanIntent(activity)
                        .addOnSuccessListener { intentSender ->
                            scannerLauncher.launch(
                                IntentSenderRequest.Builder(intentSender).build()
                            )
                        }

                        //si falla sale un aviso
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Error al iniciar el escáner",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }) {
                    Text("Escanear Documento")
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Mostrar la imagen escaneada
                scannedImageUri?.let { uri -> //carga la imagen desde la URI de forma asincrónica.
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Documento escaneado",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                }
            }
        }
    }
}