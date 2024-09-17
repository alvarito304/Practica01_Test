package org.example.clientes.repository

import org.example.clientes.model.Cliente
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.util.*

class ClientesRepositoryImpl: IClientesRepository {
    private val logger = logging()
    //"conexion" a la base de datos en este caso es una lista en memoria
    private val db = mutableListOf<Cliente>()

    override fun getByDni(dni: String): Cliente? {
        logger.debug{"Buscando cliente por dni: $dni"}
        logger.info{"total de coincidencias: ${db.count { it.dni == dni }}"}
        return db.find { it.dni == dni }
    }

    override fun getAll(): List<Cliente> {
        logger.debug{"Buscando todos los clientes"}
        logger.info{"total de clientes: ${db.size}"}
        return db.toList()
    }

    override fun getById(id: Int): Cliente? {
        logger.debug{"Buscando cliente por id: $id"}
        logger.info{"total de coincidencias: ${db.count { it.id == id }}"}
        return db.find { it.id == id }
    }

    override fun save(t: Cliente): Cliente {
        logger.debug{"Guardando cliente: $t"}
        val timeStamp = LocalDateTime.now()
        val newClient = t.copy(createdAt = timeStamp, updatedAt = timeStamp)
        db.add(newClient)
        logger.info{"persona guardada con id: ${newClient.id} y nombre: ${newClient.nombre}"}
        return newClient
    }

    override fun update(id: Int, t: Cliente): Cliente? {
        logger.debug{"Actualizando cliente con id: $id"}
        val Client = db.find { it.id == id }
        if (Client!= null) {
            val updatedClient = t.copy(updatedAt = LocalDateTime.now())
            db[id] = updatedClient
            logger.info{"cliente actualizado con id: ${updatedClient.id} y nombre: ${updatedClient.nombre}"}
            return updatedClient
        } else {
            logger.warn{"No se encontró el cliente con id: $id"}
            return null
        }
    }

    override fun delete(id: Int): Cliente? {
        logger.debug{"Eliminando cliente con id: $id"}
        val client = db.find { it.id == id }
        if (client!= null) {
            db.remove(client)
            logger.info{"cliente eliminado con id: ${client.id} y nombre: ${client.nombre}"}
            return client
        } else {
            logger.warn{"No se encontró el cliente con id: $id"}
            return null
        }
    }
}