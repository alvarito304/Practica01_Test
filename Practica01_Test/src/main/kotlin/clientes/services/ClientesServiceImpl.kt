package org.example.clientes.services

import org.example.clientes.exceptions.PersonaException
import org.example.clientes.model.Cliente
import org.example.clientes.repository.IClientesRepository
import org.example.clientes.services.cache.ICacheCliente
import org.example.clientes.validator.validadorCliente.ValidadorCliente
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
        logger.debug{"Validando cliente: $cliente"}
        if (!ValidadorCliente.esValido(cliente)) throw PersonaException.PersonaNotSavedException()
        logger.debug{"Guardando cliente en el repositorio: $cliente"}
        val clienteSaved = repository.save(cliente)
        if (clienteSaved == null) {
            logger.error{"No se ha podido guardar el cliente"}
            throw PersonaException.PersonaNotSavedException()
        }
        logger.info{"cliente guardado: $clienteSaved"}
        return clienteSaved
    }

    override fun updateCliente(id: UUID, cliente: Cliente): Cliente {
        logger.debug { "Actualizando cliente con id: $id y datos: $cliente" }
        cache.remove(id)
        val result = repository.update(id, cliente)
        if (result == null) {
            logger.error{"No se ha podido actualizar el cliente con id: $id"}
            throw PersonaException.PersonaNotUpdatedException(id.toString())
        }
        logger.info{"cliente actualizado: $result"}
        return result
    }

    override fun deleteCliente(id: UUID): Cliente {
        logger.debug { "Eliminando cliente con id: $id" }
        val result = repository.delete(id)
        if (result == null) {
            logger.error{"No se ha podido eliminar el cliente con id: $id"}
            throw PersonaException.PersonaNotDeletedException(id.toString())
        }
        logger.info{"cliente eliminado: $result"}
        return result
    }
}