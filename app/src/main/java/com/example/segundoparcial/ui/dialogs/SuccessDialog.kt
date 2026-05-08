package com.example.segundoparcial.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.segundoparcial.model.FormState

private val Verde     = Color(0xFF1B5E20)
private val VerdeClaro = Color(0xFF4CAF50)

@Composable
fun SuccessDialog(
    formState: FormState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White,
        icon = {
            Text("✅", fontSize = 40.sp)
        },
        title = {
            Text(
                text = "¡Envío exitoso!",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Verde,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Los datos han sido enviados correctamente:",
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                DataRow(emoji = "👤", label = "Nombre",   value = formState.nombre)
                DataRow(emoji = "✉️", label = "Correo",   value = formState.correo)
                DataRow(emoji = "📱", label = "Teléfono", value = formState.telefono)
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeClaro),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aceptar", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    )
}

@Composable
private fun DataRow(emoji: String, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(emoji, fontSize = 16.sp)
        Column {
            Text(label, fontSize = 11.sp, color = Color(0xFF888888), fontWeight = FontWeight.Medium)
            Text(value, fontSize = 14.sp, color = Color(0xFF222222), fontWeight = FontWeight.SemiBold)
        }
    }
}