package com.example.segundoparcial.model

data class FormState(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",

    val nombreError: String? = null,
    val correoError: String? = null,
    val telefonoError: String? = null
) {
    val isValid: Boolean
        get() = nombreError == null && correoError == null && telefonoError == null
                && nombre.isNotBlank() && correo.isNotBlank() && telefono.isNotBlank()
}