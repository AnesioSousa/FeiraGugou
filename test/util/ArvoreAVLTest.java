package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anesio
 */
public class ArvoreAVLTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Teste do método inserir, da classe ArvoreAVL.
     */
    @Test
    public void testInserir() {
        System.out.println("inserir");
        String chave = "anesio";
        ArvoreAVL tree = new ArvoreAVL();

        boolean esperado = true;
        boolean resultado = tree.inserir(chave);
        assertEquals(esperado, resultado);       // Tenta inserir;

        int tamEsperado = 1;
        int tamResultado = tree.tamanho();
        assertSame(tamEsperado, tamResultado);  // Verifica tamanho após a inserção correta;

        esperado = false;
        resultado = tree.inserir(chave);
        assertEquals(esperado, resultado);      // Tenta inserir valor repetido;

        esperado = false;
        resultado = tree.inserir(null);
        assertEquals(esperado, resultado);     // Tenta inserir valor nulo;

        tree.inserir("beatriz");
        tree.inserir("carlos");
        String esperadoNode = "beatriz";
        ArvoreAVL.Node resultadoNode = tree.getRaiz();
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());  // Verifica estado da raiz após inserções (Testa rotações);

        esperadoNode = "anesio";
        resultadoNode = tree.getRaiz().getEsquerda();
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());  // Verifica estado do filho da esquerda após inserções (Testa rotações);

        esperadoNode = "carlos";
        resultadoNode = tree.getRaiz().getDireita();
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());  // Verifica estado do filho da direita após inserções (Testa rotações);     
    }

    /**
     * Teste do método remover, da classe ArvoreAVL.
     */
    @Test
    public void testRemover() {
        System.out.println("remover");
        String chave = "anesio";
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(chave);

        boolean esperado = true;
        boolean resultado = tree.remover(chave);
        assertEquals(esperado, resultado);     // Tenta remover;

        int tamEsperado = 0;
        int tamResultado = tree.tamanho();
        assertSame(tamEsperado, tamResultado); // Verifica tamanho após a remoção correta;

        esperado = false;
        resultado = tree.remover(chave);
        assertEquals(esperado, resultado);     // Tenta remover um item inexistente;

        esperado = false;
        resultado = tree.remover(null);
        assertEquals(esperado, resultado);     // Tenta remover valor nulo;

        tree.inserir("anesio");
        tree.inserir("beatriz");
        tree.inserir("carlos");
        tree.inserir("daniel");

        esperado = true;
        resultado = tree.remover("daniel");                                // ---> Remove nó folha; <---
        assertEquals(esperado, resultado);

        String esperadoNode = "beatriz";
        ArvoreAVL.Node resultadoNode = tree.getRaiz();
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());               // Verifica estado da raiz após remoções (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getDado().getPalavraChave()); // Verifica estado do filho da esquerda após inserções (Testa rotações);

        esperadoNode = "carlos";
        assertEquals(esperadoNode, resultadoNode.getDireita().getDado().getPalavraChave());  // Verifica estado do filho da direita após inserções (Testa rotações);

        assertNull(resultadoNode.getDireita().getDireita());               // Verifica se o filho da direita (daniel) do nó à direita da raiz (carlos) foi removido (Testa rotações);

        tree.inserir("daniel");
        tree.remover("carlos");                                            // ---> Remove nó com apenas um filho; <---

        esperadoNode = "beatriz";
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());               // Verifica estado da raiz após remoção (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getDado().getPalavraChave()); // Verifica estado do filho da esquerda da raiz após a remoção (Testa rotações);

        esperadoNode = "daniel";
        assertEquals(esperadoNode, resultadoNode.getDireita().getDado().getPalavraChave());  // Verifica estado do filho da direita da raiz após a remoção (Testa rotações);

        tree.inserir("carlos");
        tree.inserir("emanuel");
        tree.remover("daniel");                                                        // ---> Remove nó com dois filhos; <---

        esperadoNode = "beatriz";
        assertEquals(esperadoNode, resultadoNode.getDado().getPalavraChave());                           // Verifica estado da raiz após remoção (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getDado().getPalavraChave());             // Verifica estado do filho da esquerda da raiz após a remoção (Testa rotações);

        esperadoNode = "carlos";
        assertEquals(esperadoNode, resultadoNode.getDireita().getDado().getPalavraChave());              // Verifica estado do filho da direita da raiz após a remoção (Testa rotações);

        esperadoNode = "emanuel";
        assertEquals(esperadoNode, resultadoNode.getDireita().getDireita().getDado().getPalavraChave()); // Verifica estado do filho da direita (emanuel) do nó à direita da raiz (carlos),  (Testa rotações);
    }

    /**
     * Teste do método contains, da classe ArvoreAVL.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        String chave = "anesio";
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(chave);                      // Adiciona um nó para testes;

        boolean esperado = true;
        boolean resultado = tree.contains(chave);
        assertEquals(esperado, resultado);        // Verifica a existência do nó adicionado anteriormente;

        tree.remover(chave);                      // O nó anteriormente adicionado, é removido.
        esperado = false;
        resultado = tree.contains(chave);
        assertEquals(esperado, resultado);        // Verifica a existência do nó removido anteriormente;

        chave = null;
        esperado = false;
        resultado = tree.contains(chave);
        assertEquals(esperado, resultado);        // Tenta procurar algo que não existe;
    }

    /**
     * Teste do método altura, da classe ArvoreAVL.
     */
    @Test
    public void testAltura() {
        System.out.println("altura");
        String chave = "anesio";
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(chave);                 // Insere um nó na raiz;

        int esperado = 1;
        int resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura da raiz;

        tree.inserir("beatriz");             // Insere mais um nó na raiz;
        esperado = 1;
        resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura;

        tree.inserir("gustavo");
        tree.inserir("oswaldo");
        tree.inserir("camila");              // Insere mais 3 nós na raiz e verifica sua altura (número de arestas entre a raiz e um nó folha);
        esperado = 2;
        resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura;
    }

    /**
     * Teste do método tamanho, da classe ArvoreAVL.
     */
    @Test
    public void testTamanho() {
        System.out.println("tamanho");
        ArvoreAVL tree = new ArvoreAVL();

        int esperado = 0;
        int resultado = tree.tamanho();
        assertSame(esperado, resultado);

        tree.inserir("anesio");             // Insere nós na árvore;
        esperado = 1;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // Verifica o tamanho da árvore;

        String[] pessoas = new String[]{"gustavo", "maria", "joao", "pedro", "gabriel", "marcus", "lucas", "levi"};

        for (String palavra : pessoas) {
            tree.inserir(palavra);          // Insere nós na árvore;
        }

        esperado = 9;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // Verifica o tamanho da árvore;

        tree.remover("pedro");
        tree.remover("joao");               // Remove dois nós da árvore;
        esperado = 7;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // E verifica a contagem de nós;

        for (String pessoa : pessoas) {
            tree.remover(pessoa);
        }
        tree.remover("anesio");
        // Remove todos os dados da árvore;
        esperado = 0;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // E verifica se o tamanho é igual a 0;
    }

    /**
     * Teste do método isEmpty, da classe ArvoreAVL.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        ArvoreAVL tree = new ArvoreAVL();

        boolean esperado = true;
        boolean resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Nada é inserido na árvore, então é verificado se está vazia;

        String[] vila = new String[]{"chaves", "chiquinha", "kiko"};   // É criado um vetor de palavras para auxiliar no preenchimento de dados na árvore;

        for (String personagem : vila) {
            tree.inserir(personagem);                                  // Os dados do vetor são inseridos na árvore;
        }
        esperado = false;
        resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Verifica se a árvore está vazia;

        for (String personagem : vila) {
            tree.remover(personagem);                                  // Todas as palavras ão removidas da árvore;
        }
        esperado = true;
        resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Verifica se a árvore está vazia;
    }
    /*
    /**
     * Teste do método encontrar, da classe ArvoreAVL.
     *
    @Test
    public void testEncontrar() {
        System.out.println("getNode");
        ArvoreAVL tree = new ArvoreAVL();
        String chave = "anesio";
        tree.inserir(chave);
        
        String esperado = "anesio";
        ArvoreAVL.Node resultado = tree.encontrar(chave);
        assertEquals(esperado, resultado.getDado().getPalavraChave());
        
        String[] vila = new String[]{"chaves", "chiquinha", "kiko"};
        
        for (String personagem : vila) {
            tree.inserir(personagem);                                  
        }
        
        chave = "kiko";
        esperado = "kiko";
        resultado = tree.encontrar(chave);
        assertEquals(esperado, resultado.getDado().getPalavraChave());
    }
    */
    
    /**
     * Teste do método getRaiz, da classe ArvoreAVL.
     */
    @Test
    public void testGetRaiz() {
        System.out.println("getRaiz");
        ArvoreAVL tree = new ArvoreAVL();

        String esperado = null;                                        // Nada é inserido na árvore;
        ArvoreAVL.Node resultado = tree.getRaiz();
        assertEquals(esperado, resultado);                             // Então é verificado se a raiz possui null;

        tree.inserir("anesio");                                        // É inserido um nó, e como ele é o primeiro, ele passa a ser a raiz;
        esperado = "anesio";
        resultado = tree.getRaiz();
        assertEquals(esperado, resultado.getDado().getPalavraChave());                   // É verificado o estado da raiz;

        tree.inserir("beatriz");
        tree.inserir("carlos");                                        // São inseridos mais dois nós, e o valor da raiz muda por causa das rotações;
        esperado = "beatriz";
        resultado = tree.getRaiz();
        assertEquals(esperado, resultado.getDado().getPalavraChave());                   // É verificado o estado da raiz;

    }

}