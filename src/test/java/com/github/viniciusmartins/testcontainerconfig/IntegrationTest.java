package com.github.viniciusmartins.testcontainerconfig;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntegrationTest extends TestContainerBase {

    // testes pra verificar se inicializa uma vez só

    @Test
    void test1() {
        assertNotNull("notNull");
    }

    @Test
    void test2() {
        assertNotNull("notNull");
    }

    @Test
    void test3() {
        assertNotNull("notNull");
    }

}
