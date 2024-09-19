package org.example.clientes.services.cache

import org.example.clientes.model.Cliente
import java.util.*

interface ICacheCliente : Cache<UUID, Cliente>{
    fun getByDni(dni: String): Cliente?
}