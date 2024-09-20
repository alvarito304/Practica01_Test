package org.example.clientes.exceptions

sealed class PersonaException(message: String) : Exception(message) {
    class PersonaNotFoundException(id: String) : PersonaException("Persona no encontrada con ID: $id")
    class PersonaNotSavedException: PersonaException("Persona no guardada")
    class PersonaNotUpdatedException(id: String) : PersonaException("Persona no actualizada con ID: $id")
    class PersonaNotDeletedException(id: String) : PersonaException("Persona no eliminada con ID: $id")
}