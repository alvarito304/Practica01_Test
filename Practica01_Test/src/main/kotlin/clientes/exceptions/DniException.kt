package org.example.clientes.exceptions

sealed class DniException(message: String) : Exception(message) {
    class DniInvalidoException(dni: String) : DniException("DNI inválido: $dni")
}