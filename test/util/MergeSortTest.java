package util;

import java.util.ArrayList;
import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe MergeSort.
 * @author Anésio Sousa
 */
public class MergeSortTest {

    /**
     * Teste do método sort (sem uso de comparator), da classe MergeSort.
     */
    @Test
    public void testSort_ArrayList() {
        MergeSort merge = new MergeSort();
        ArrayList<Integer> numeros = new ArrayList<>();

        numeros.add(45);
        numeros.add(77);
        numeros.add(41);
        numeros.add(31);
        numeros.add(29);

        merge.sort(numeros);

        int esperado = 29;
        int resultado = numeros.get(0);
        assertSame(esperado, resultado);

        esperado = 77;
        resultado = numeros.get(4);
        assertSame(esperado, resultado);

        esperado = 41;
        resultado = numeros.get(2);
        assertSame(esperado, resultado);
    }

    /**
     * Teste do método sort (com uso de comparator), da classe MergeSort.
     */
    @Test
    public void testSort_ArrayList_Comparator() {
        MergeSort merge = new MergeSort();
        ArrayList<String> nomes = new ArrayList<>();

        nomes.add("Anésio");
        nomes.add("Uellington");
        nomes.add("Ivanildo");
        nomes.add("Elvis");
        nomes.add("Matheus");
        nomes.add("Fernanda");
        nomes.add("José");

        Comparator<String> ordenarPorLength = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return - 1;
                }
                if (o1.length() < o2.length()) {
                    return - -1;
                }
                return 0;
            }
        };

        merge.sort(nomes, ordenarPorLength);

        String esperado = "Uellington";
        String resultado = nomes.get(0);
        assertEquals(esperado, resultado);

        esperado = "Matheus";
        resultado = nomes.get(3);
        assertEquals(esperado, resultado);

        esperado = "José";
        resultado = nomes.get(6);
        assertEquals(esperado, resultado);
    }
}
