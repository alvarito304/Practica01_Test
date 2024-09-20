package org.example.clientes.repository
import org.example.clientes.model.Cliente
import org.example.clientes.repository.common.IRepository
import java.util.UUID

interface IClientesRepository : IRepository<UUID, Cliente> {
    fun getByDni(dni: String): Cliente?
}