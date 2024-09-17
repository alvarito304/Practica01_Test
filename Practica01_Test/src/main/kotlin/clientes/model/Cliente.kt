package org.example.clientes.model

import java.time.LocalDateTime

data class Cliente(
    val id: Int,
    val nombre: String,
    val dni: String,
    var cuentaBancaria: CuentaBancaria,
    var tarjeta: Tarjeta,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now()

)