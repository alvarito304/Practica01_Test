package clientes.validator.validadorDniNif

import org.example.clientes.validator.validadorDniNif.ValidadorDniNif
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ValidadorDniNifTest {

    @Test
    fun esValido() {
        //arrange
        val dni = "12345678Z"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoFormatoIncorrecto(){
        //arrange
        val dni = "2345678Z"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }

    @Test
    fun esValidoLetraX(){
        //arrange
        val dni = "X1234567L"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoLetraY(){
        //arrange
        val dni = "Y1234567X"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)

    }@Test
    fun esValidoLetraZ(){
        //arrange
        val dni = "Z1234567R"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoLetraIncorrecta(){
        //arrange
        val dni = "X1234567M"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }
    @Test
    fun esValidoNifFormatoInvalido(){
        //arrange
        val dni = "X123567L"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }


}