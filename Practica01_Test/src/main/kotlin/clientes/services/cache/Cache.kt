package org.example.clientes.services.cache

interface Cache<k,v> {
    fun get(key: k): v?
    fun put(key: k , value: v)
    fun remove(key: k)
    fun clear()
    fun size(): Int
}