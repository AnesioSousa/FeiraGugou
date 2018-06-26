package util;

import model.Dados;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe Node.
 * @author Anésio Sousa
 */
public class NodeTest {
    Node n1;

    @Before
    public void setUp() {
        n1 = new Node("Anésio");
    }

    /**
     * Testes dos atributos da classe Node.
     */
    @Test
    public void testBasic() {
        n1.setAltura(5);
        n1.setFator(1);

        assertEquals("Anésio", n1.getChave());
        assertSame(5, n1.getAltura());
        assertSame(1, n1.getFator());
        assertSame(0, n1.getVezesBuscada());

        n1.incrementVezesBuscada();

        assertSame(1, n1.getVezesBuscada());
        assertSame(0, n1.getListaDados().size());

        Dados d = new Dados();

        n1.getListaDados().add(d);
        assertEquals(d, n1.getListaDados().get(0));
    }
}
