package org.example.clientes.repository.common

interface IRepository <ID, T>{
    fun getAll(): List<T>
    fun getById(id:ID): T?
    fun save (t:T): T
    fun update(id:ID, t:T): T?
    fun delete(id:ID): T?
}