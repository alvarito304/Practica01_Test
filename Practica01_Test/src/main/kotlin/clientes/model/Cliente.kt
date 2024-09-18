package org.example.clientes.model

import java.time.LocalDateTime
import java.util.*

data class Cliente(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val dni: String,
    var cuentaBancaria: CuentaBancaria,
    var tarjeta: Tarjeta,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now()

)