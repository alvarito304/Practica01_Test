package org.example.clientes.validator.validadorDniNif

object ValidadorDniNif {
    private val dniPattern = Regex("^(\\d{8}[A-HJ-NP-TV-Z])|([XYZ]\\d{7}[A-HJ-NP-TV-Z])$")

    fun esValido(dniNif: String): Boolean {
        if (!dniNif.matches(dniPattern)) return false

        // Convertir la letra inicial del NIF si existe
        val numero = when (dniNif.first()) {
            'X' -> "0${dniNif.substring(1, 8)}".toInt()
            'Y' -> "1${dniNif.substring(1, 8)}".toInt()
            'Z' -> "2${dniNif.substring(1, 8)}".toInt()
            else -> dniNif.substring(0, 8).toInt()  // Para DNI normal
        }
        val letra = dniNif.last()
        return calcularLetraControl(numero) == letra
    }

    private fun calcularLetraControl(numero: Int): Char {
        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        return letras[numero % 23]
    }
}