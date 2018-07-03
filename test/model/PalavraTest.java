package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Teste da classe Palavra.
 * @author Anésio Sousa
 */
public class PalavraTest {
    Palavra p;
    
    /**
     * Este método é executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que são utilizados nos testes.
     */
    @Before
    public void setUp() {
        p = new Palavra();
    }

    /**
     * Testes de manipulação de atributos da classe Palavra.
     */
    @Test
    public void testBasic() {
        p.setChave("Objeto");
        p.incrementVezesBuscada();
        
        assertEquals("Objeto", p.getChave());
        assertSame(1, p.getVezesBuscada());
        
        p.incrementVezesBuscada();
        assertSame(2, p.getVezesBuscada());

        Palavra temp = new Palavra();
        
        temp.setChave("Pessoa");
        temp.incrementVezesBuscada();
        
        assertFalse(p.equals(temp));
        temp.setChave("Objeto");
        assertTrue(p.equals(temp));
        temp.setChave("Faça Yoga!");
        int i = p.compareTo(temp);
        assertTrue( i > 0);
        
    }
}