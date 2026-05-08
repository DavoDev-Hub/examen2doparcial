package com.example.segundoparcial.utils

object Validators {

    /** Solo letras (a-z, A-Z, acentos, ñ) y espacios. Mínimo 2 caracteres. */
    fun validateNombre(value: String): String? {
        if (value.isBlank()) return "El nombre no puede estar vacío"
        if (!value.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$")))
            return "Solo se permiten letras y espacios"
        if (value.trim().length < 2) return "El nombre es demasiado corto"
        return null
    }

    /** Formato estándar: algo@dominio.ext */
    fun validateCorreo(value: String): String? {
        if (value.isBlank()) return "El correo no puede estar vacío"
        if (!value.matches(Regex("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")))
            return "Formato inválido (ej: correo@dominio.com)"
        return null
    }

    /** Exactamente 10 dígitos numéricos */
    fun validateTelefono(value: String): String? {
        if (value.isBlank()) return "El teléfono no puede estar vacío"
        if (!value.matches(Regex("^\\d{10}$")))
            return "Debe tener exactamente 10 dígitos"
        return null
    }
}