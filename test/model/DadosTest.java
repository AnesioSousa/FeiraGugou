package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe Dados.
 *
 * @author Anésio Sousa
 */
public class DadosTest {

    Dados d;

    /**
     * Este método é executado antes de cada teste de unidade (testes a seguir), 
     * e serve para inicializar objetos que são utilizados nos testes.
     */
    @Before
    public void setUp() {
        d = new Dados();
    }

    /**
     * Testes de manipulação de atributos da classe Dados.
     */
    @Test
    public void testBasic() {
        d.setTitulo("Como fazer bolo de cenoura");
        d.setQuantidade(12);
        assertEquals("Como fazer bolo de cenoura", d.getTitulo());
        assertSame(12, d.getQuantidade());

        Dados temp = new Dados();
        temp.setTitulo("Temperando a vida!");
        temp.setQuantidade(7);
        assertFalse(d.equals(temp));
        temp.setTitulo("Como fazer bolo de cenoura");
        assertTrue(d.equals(temp));
        assertSame(-1, d.compareTo(temp));
    }
}
