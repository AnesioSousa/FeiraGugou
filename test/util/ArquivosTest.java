package util;

import java.io.File;
import java.util.ArrayList;
import model.Pagina;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anesio
 */
public class ArquivosTest {
    
    public ArquivosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of obterRepositorio method, of class Arquivos.
     */
    @Test
    public void testObterRepositorio() {
        System.out.println("obterRepositorio");
        Arquivos instance = new Arquivos();
        ArrayList<File> expResult = null;
        ArrayList<File> result = instance.obterRepositorio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passarArqParaPaginas method, of class Arquivos.
     */
    @Test
    public void testPassarArqParaPaginas() {
        System.out.println("passarArqParaPaginas");
        ArrayList<Pagina> lista = null;
        ArrayList<File> arq = null;
        Arquivos instance = new Arquivos();
        ArrayList<Pagina> expResult = null;
        ArrayList<Pagina> result = instance.passarArqParaPaginas(lista, arq);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
