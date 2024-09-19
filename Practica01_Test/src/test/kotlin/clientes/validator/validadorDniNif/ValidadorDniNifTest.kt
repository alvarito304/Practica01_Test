package clientes.validator.validadorDniNif

import org.example.clientes.validator.validadorDniNif.ValidadorDniNif
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ValidadorDniNifTest {

    @Test
    fun esValido() {
        //assert
        val dni = "12345678Z"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoFormatoIncorrecto(){
        //assert
        val dni = "2345678Z"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }

    @Test
    fun esValidoLetraX(){
        //assert
        val dni = "X1234567L"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoLetraY(){
        //assert
        val dni = "Y1234567X"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)

    }@Test
    fun esValidoLetraZ(){
        //assert
        val dni = "Z1234567R"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertTrue(result)
    }
    @Test
    fun esValidoLetraIncorrecta(){
        //assert
        val dni = "X1234567M"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }
    @Test
    fun esValidoNifFormatoInvalido(){
        //assert
        val dni = "X123567L"
        //act
        val result = ValidadorDniNif.esValido(dni)
        //assert
        assertFalse(result)
    }


}