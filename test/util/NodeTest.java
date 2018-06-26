/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Iterator;
import model.Dados;
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
public class NodeTest {
    
    public NodeTest() {
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
     * Test of getChave method, of class Node.
     */
    @Test
    public void testGetChave() {
        System.out.println("getChave");
        Node instance = null;
        String expResult = "";
        String result = instance.getChave();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setChave method, of class Node.
     */
    @Test
    public void testSetChave() {
        System.out.println("setChave");
        String chave = "";
        Node instance = null;
        instance.setChave(chave);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAltura method, of class Node.
     */
    @Test
    public void testGetAltura() {
        System.out.println("getAltura");
        Node instance = null;
        int expResult = 0;
        int result = instance.getAltura();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAltura method, of class Node.
     */
    @Test
    public void testSetAltura() {
        System.out.println("setAltura");
        int altura = 0;
        Node instance = null;
        instance.setAltura(altura);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFator method, of class Node.
     */
    @Test
    public void testGetFator() {
        System.out.println("getFator");
        Node instance = null;
        int expResult = 0;
        int result = instance.getFator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFator method, of class Node.
     */
    @Test
    public void testSetFator() {
        System.out.println("setFator");
        int fator = 0;
        Node instance = null;
        instance.setFator(fator);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEsquerda method, of class Node.
     */
    @Test
    public void testGetEsquerda() {
        System.out.println("getEsquerda");
        Node instance = null;
        Node expResult = null;
        Node result = instance.getEsquerda();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEsquerda method, of class Node.
     */
    @Test
    public void testSetEsquerda() {
        System.out.println("setEsquerda");
        Node esquerda = null;
        Node instance = null;
        instance.setEsquerda(esquerda);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDireita method, of class Node.
     */
    @Test
    public void testGetDireita() {
        System.out.println("getDireita");
        Node instance = null;
        Node expResult = null;
        Node result = instance.getDireita();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDireita method, of class Node.
     */
    @Test
    public void testSetDireita() {
        System.out.println("setDireita");
        Node direita = null;
        Node instance = null;
        instance.setDireita(direita);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVezesBuscada method, of class Node.
     */
    @Test
    public void testGetVezesBuscada() {
        System.out.println("getVezesBuscada");
        Node instance = null;
        int expResult = 0;
        int result = instance.getVezesBuscada();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementVezesBuscada method, of class Node.
     */
    @Test
    public void testIncrementVezesBuscada() {
        System.out.println("incrementVezesBuscada");
        Node instance = null;
        instance.incrementVezesBuscada();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListaDados method, of class Node.
     */
    @Test
    public void testGetListaDados() {
        System.out.println("getListaDados");
        Node instance = null;
        ArrayList<Dados> expResult = null;
        ArrayList<Dados> result = instance.getListaDados();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listarDados method, of class Node.
     */
    @Test
    public void testListarDados() {
        System.out.println("listarDados");
        Node instance = null;
        Iterator expResult = null;
        Iterator result = instance.listarDados();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Node.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Node instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
