package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Teste da classe Pagina.
 * @author Anésio Sousa
 */
public class PaginaTest {
    Pagina p;

    /**
     * Este método é executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que são utilizados nos testes.
     */
    @Before
    public void setUp() {
        p = new Pagina();
    }
    
    /**
     * Testes de manipulação de atributos da classe Pagina.
     */
    @Test
    public void testBasic() {
        p.setTitulo("Está traiçoeiro o trabalho com JavaFx");
        p.incrementVezesAcessada();
        p.setInfo(1522511100);
        assertEquals("Está traiçoeiro o trabalho com JavaFx", p.getTitulo());
        assertSame(1, p.getVezesAcessada());
        assertEquals(p.getInfo(), 1522511100);

        Pagina temp = new Pagina();
        temp.setTitulo("Temperando a vida!");
        temp.incrementVezesAcessada();
        
        assertFalse(p.equals(temp));
        temp.setTitulo("Está traiçoeiro o trabalho com JavaFx");
        assertTrue(p.equals(temp));
        temp.setTitulo("Faça Yoga!");
        int i = p.compareTo(temp);
        assertTrue( i < 0);
        
    }

}