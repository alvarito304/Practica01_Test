package org.example.clientes.repository
import org.example.clientes.model.Cliente
import org.example.clientes.repository.common.IRepository

interface IClientesRepository : IRepository<Int, Cliente> {
    fun getByDni(dni: String): Cliente?
}