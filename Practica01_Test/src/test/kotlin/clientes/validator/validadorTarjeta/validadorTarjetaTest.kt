package clientes.validator.validadorTarjeta

import org.example.clientes.validator.validadorTarjeta.ValidadorTarjeta
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class validadorTarjetaTest {

    @Test
    fun esNumeroValido() {
        //arrange
        val numero = "5555-5555-5555-4444"

        //act
        val result = ValidadorTarjeta.esNumeroValido(numero)

        //assert
        assertTrue(result)
    }

    @Test
    fun esNumeroValidoFormatoIncorrecto(){
        //arrange
        val numero = "4111-1111-1111-111"

        //act
        val result = ValidadorTarjeta.esNumeroValido(numero)

        //assert
        assertFalse(result)
    }

    @Test
    fun esFechaValida() {
        //arrange
        val fecha = "09/25"
        //act
        val result = ValidadorTarjeta.esFechaValida(fecha)
        //assert
        assertTrue(result)
    }

    @Test
    fun esFechaValidaFormatoIncorrecto(){
        //arrange
        val fecha = "09/23"
        //act
        val result = ValidadorTarjeta.esFechaValida(fecha)
        //assert
        assertFalse(result)
    }
}