package com.fiap.challenge.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CpfTest {

    @Test
    public void deveCriarCpfValido() {
        Cpf cpf = new Cpf("52998224725");
        Assertions.assertEquals("52998224725", cpf.getValue());
    }

    @Test
    public void deveLancarExcecao_ParaCpfInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Cpf("12345678901"));
    }

    @Test
    public void deveLancarExcecao_ParaCpfNulo() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Cpf(null));
    }

    @Test
    public void deveLancarExcecao_ParaCpfComDigitosRepetidos() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Cpf("11111111111"));
    }
}
