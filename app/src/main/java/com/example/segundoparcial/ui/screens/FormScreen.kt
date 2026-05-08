package com.example.segundoparcial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.segundoparcial.model.FormState
import com.example.segundoparcial.ui.components.ValidatedTextField
import com.example.segundoparcial.ui.dialogs.SuccessDialog
import com.example.segundoparcial.utils.Validators

private val Verde      = Color(0xFF1B5E20)
private val VerdeClaro = Color(0xFF4CAF50)
private val Fondo      = Color(0xFFF1F8E9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen() {
    var formState by remember { mutableStateOf(FormState()) }
    var showDialog by remember { mutableStateOf(false) }
    // ¿Ya intentó enviar al menos una vez?
    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Fondo,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Verde,
                    titleContentColor = Color.White
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📋", fontSize = 22.sp)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                "Formulario de registro",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                "Completa todos los campos",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Tarjeta del formulario ──────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Datos personales",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Verde
                    )
                    Spacer(Modifier.height(8.dp))

                    // ── Nombre ──────────────────────────────────────────────
                    ValidatedTextField(
                        value = formState.nombre,
                        onValueChange = { nuevo ->
                            formState = formState.copy(
                                nombre = nuevo,
                                nombreError = if (submitted) Validators.validateNombre(nuevo) else null
                            )
                        },
                        label = "Nombre completo",
                        leadingEmoji = "👤",
                        errorMessage = formState.nombreError
                    )

                    // ── Correo ───────────────────────────────────────────────
                    ValidatedTextField(
                        value = formState.correo,
                        onValueChange = { nuevo ->
                            formState = formState.copy(
                                correo = nuevo,
                                correoError = if (submitted) Validators.validateCorreo(nuevo) else null
                            )
                        },
                        label = "Correo electrónico",
                        leadingEmoji = "✉️",
                        errorMessage = formState.correoError,
                        keyboardType = KeyboardType.Email
                    )

                    // ── Teléfono ─────────────────────────────────────────────
                    ValidatedTextField(
                        value = formState.telefono,
                        onValueChange = { nuevo ->
                            // Solo permitir dígitos
                            val soloDigitos = nuevo.filter { it.isDigit() }
                            formState = formState.copy(
                                telefono = soloDigitos,
                                telefonoError = if (submitted) Validators.validateTelefono(soloDigitos) else null
                            )
                        },
                        label = "Número de teléfono (10 dígitos)",
                        leadingEmoji = "📱",
                        errorMessage = formState.telefonoError,
                        keyboardType = KeyboardType.Phone,
                        maxLength = 10
                    )
                }
            }

            // ── Botón Enviar ────────────────────────────────────────────────
            Button(
                onClick = {
                    submitted = true
                    // Validar todos los campos al hacer clic
                    val nombreErr   = Validators.validateNombre(formState.nombre)
                    val correoErr   = Validators.validateCorreo(formState.correo)
                    val telefonoErr = Validators.validateTelefono(formState.telefono)

                    formState = formState.copy(
                        nombreError   = nombreErr,
                        correoError   = correoErr,
                        telefonoError = telefonoErr
                    )

                    if (nombreErr == null && correoErr == null && telefonoErr == null) {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeClaro),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    "Enviar datos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // ── Indicador de estado del formulario ──────────────────────────
            if (submitted && !formState.isValid) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("❌", fontSize = 20.sp)
                        Column {
                            Text(
                                "Hay errores en el formulario",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFB71C1C)
                            )
                            Text(
                                "Revisa los campos marcados en rojo.",
                                fontSize = 12.sp,
                                color = Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }
        }
    }

    // ── Diálogo de éxito ────────────────────────────────────────────────────
    if (showDialog) {
        SuccessDialog(
            formState = formState,
            onDismiss = {
                showDialog = false
                // Resetear el formulario
                formState = FormState()
                submitted = false
            }
        )
    }
}