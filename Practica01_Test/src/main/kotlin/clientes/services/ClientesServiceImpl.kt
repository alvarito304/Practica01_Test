package org.example.clientes.services

import org.example.clientes.exceptions.PersonaException
import org.example.clientes.model.Cliente
import org.example.clientes.repository.IClientesRepository
import org.example.clientes.services.cache.ICacheCliente
import org.lighthousegames.logging.logging
import java.util.*

class ClientesServiceImpl(private val cache: ICacheCliente, private val repository: IClientesRepository): IClienteService  {
    private val logger = logging()

    override fun getAllClientes(): List<Cliente> {
        logger.debug{"Buscando todos los clientes"}
        val clientes = repository.getAll()
        logger.info{"clientes encontrados: ${clientes}"}
        return clientes
    }

    override fun getClienteByDniNif(dni: String): Cliente {
        logger.debug{"Buscando por dni en cache: $dni"}
        val cliente = cache.getByDni(dni)
        if (cliente != null) {
            logger.info{"cliente encontrado en cache: $cliente"}
            return cliente
        }
        logger.debug { "buscando por dni en repositorio: $dni" }
        val clienteFromRepository = repository.getByDni(dni)
        if (clienteFromRepository != null) {
            logger.info{"cliente encontrado en repositorio: $clienteFromRepository"}
            cache.put(clienteFromRepository.id, clienteFromRepository)
            return clienteFromRepository
        }
        logger.error { "no se ha encontrado cliente con dni: $dni" }
        throw PersonaException.PersonaNotFoundException(dni)
    }

    override fun saveCliente(cliente: Cliente): Cliente {
        logger.debug{"Guardando cliente en el repositorio: $cliente"}
        val clienteSaved = repository.save(cliente)
        logger.info{"cliente guardado: $clienteSaved"}
        return clienteSaved
    }

    override fun updateCliente(id: UUID, cliente: Cliente): Cliente {
        logger.debug { "Actualizando cliente con id: $id y datos: $cliente" }
        cache.remove(id)
        val result = repository.update(id, cliente)
        if (result == null) {
            throw PersonaException.PersonaNotUpdatedException(id.toString())
        }
        logger.info{"cliente actualizado: $result"}
        return result
    }

    override fun deleteCliente(id: UUID): Cliente {
        logger.debug { "Eliminando cliente con id: $id" }
        val result = repository.delete(id)
        if (result == null) {
            throw PersonaException.PersonaNotDeletedException(id.toString())
        }
        logger.info{"cliente eliminado: $result"}
        return result
    }

}