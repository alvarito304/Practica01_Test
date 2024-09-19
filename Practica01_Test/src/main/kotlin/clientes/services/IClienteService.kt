package org.example.clientes.services

import org.example.clientes.model.Cliente
import java.util.UUID

interface IClienteService {
    fun getAllClientes(): List<Cliente>
    fun getClienteByDniNif(dni: String): Cliente
    fun saveCliente(cliente: Cliente): Cliente
    fun updateCliente(id:UUID, cliente: Cliente): Cliente
    fun deleteCliente(id:UUID):Cliente
}