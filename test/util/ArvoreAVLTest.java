package util;

import java.util.Iterator;
import model.Palavra;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ArvoreAVL.Node;

/**
 * Testes da classe ArvoreAVL.
 * @author Anésio Sousa
 */
public class ArvoreAVLTest {
    /**
     * Teste do método inserir, da classe ArvoreAVL.
     */
    @Test
    public void testInserir() {
        String chave = "anesio";
        Palavra p = new Palavra(chave);
        ArvoreAVL tree = new ArvoreAVL();

        boolean esperado = true;
        boolean resultado = tree.inserir(p);
        assertEquals(esperado, resultado);       // Tenta inserir;

        int tamEsperado = 1;
        int tamResultado = tree.tamanho();
        assertSame(tamEsperado, tamResultado);  // Verifica tamanho após a inserção correta;

        esperado = false;
        resultado = tree.inserir(p);
        assertEquals(esperado, resultado);      // Tenta inserir valor repetido;

        esperado = false;
        resultado = tree.inserir(null);
        assertEquals(esperado, resultado);     // Tenta inserir valor nulo;
        
        tree.inserir(new Palavra("beatriz"));
        tree.inserir(new Palavra("carlos"));
        String esperadoNode = "beatriz";
        Node resultadoNode = tree.getRaiz();
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());  // Verifica estado da raiz após inserções (Testa rotações);

        esperadoNode = "anesio";
        resultadoNode = tree.getRaiz().getEsquerda();
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());  // Verifica estado do filho da esquerda após inserções (Testa rotações);

        esperadoNode = "carlos";
        resultadoNode = tree.getRaiz().getDireita();
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());  // Verifica estado do filho da direita após inserções (Testa rotações);     
    }

    /**
     * Teste do método remover, da classe ArvoreAVL.
     */
    @Test
    public void testRemover() {
        String chave = "anesio";
        Palavra p = new Palavra(chave);
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(p);

        boolean esperado = true;
        boolean resultado = tree.remover(p);
        assertEquals(esperado, resultado);     // Tenta remover;

        int tamEsperado = 0;
        int tamResultado = tree.tamanho();
        assertSame(tamEsperado, tamResultado); // Verifica tamanho após a remoção correta;

        esperado = false;
        resultado = tree.remover(p);
        assertEquals(esperado, resultado);     // Tenta remover um item inexistente;

        esperado = false;
        resultado = tree.remover(null);
        assertEquals(esperado, resultado);     // Tenta remover valor nulo;

        
        
        Palavra anesio = new Palavra("anesio");
        Palavra beatriz = new Palavra("beatriz");
        Palavra carlos = new Palavra("carlos");
        Palavra daniel = new Palavra("daniel");
        
        tree.inserir(anesio);
        tree.inserir(beatriz);
        tree.inserir(carlos);
        tree.inserir(daniel);

        esperado = true;
        resultado = tree.remover(daniel);                                // ---> Remove nó folha; <---
        assertEquals(esperado, resultado);

        String esperadoNode = "beatriz";
        Node resultadoNode = tree.getRaiz();
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());               // Verifica estado da raiz após remoções (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getKey().getChave()); // Verifica estado do filho da esquerda após inserções (Testa rotações);

        esperadoNode = "carlos";
        assertEquals(esperadoNode, resultadoNode.getDireita().getKey().getChave());  // Verifica estado do filho da direita após inserções (Testa rotações);

        assertNull(resultadoNode.getDireita().getDireita());               // Verifica se o filho da direita (daniel) do nó à direita da raiz (carlos) foi removido (Testa rotações);

        tree.inserir(daniel);
        tree.remover(carlos);                                            // ---> Remove nó com apenas um filho; <---

        esperadoNode = "beatriz";
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());               // Verifica estado da raiz após remoção (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getKey().getChave()); // Verifica estado do filho da esquerda da raiz após a remoção (Testa rotações);

        esperadoNode = "daniel";
        assertEquals(esperadoNode, resultadoNode.getDireita().getKey().getChave());  // Verifica estado do filho da direita da raiz após a remoção (Testa rotações);

        tree.inserir(carlos);
        Palavra emanuel = new Palavra("emanuel");
        tree.inserir(emanuel);
        tree.remover(daniel);                                                        // ---> Remove nó com dois filhos; <---

        esperadoNode = "beatriz";
        assertEquals(esperadoNode, resultadoNode.getKey().getChave());                           // Verifica estado da raiz após remoção (Testa rotações);

        esperadoNode = "anesio";
        assertEquals(esperadoNode, resultadoNode.getEsquerda().getKey().getChave());             // Verifica estado do filho da esquerda da raiz após a remoção (Testa rotações);

        esperadoNode = "carlos";
        assertEquals(esperadoNode, resultadoNode.getDireita().getKey().getChave());              // Verifica estado do filho da direita da raiz após a remoção (Testa rotações);

        esperadoNode = "emanuel";
        assertEquals(esperadoNode, resultadoNode.getDireita().getDireita().getKey().getChave()); // Verifica estado do filho da direita (emanuel) do nó à direita da raiz (carlos),  (Testa rotações);
    }

    /**
     * Teste do método contains, da classe ArvoreAVL.
     */
    @Test
    public void testContains() {
        String chave = "anesio";
        Palavra anesio = new Palavra(chave);
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(anesio);                      // Adiciona um nó para testes;

        boolean esperado = true;
        boolean resultado = tree.contains(anesio);
        assertEquals(esperado, resultado);        // Verifica a existência do nó adicionado anteriormente;

        tree.remover(anesio);                      // O nó anteriormente adicionado, é removido.
        esperado = false;
        resultado = tree.contains(anesio);
        assertEquals(esperado, resultado);        // Verifica a existência do nó removido anteriormente;

        chave = null;
        esperado = false;
        resultado = tree.contains(anesio);
        assertEquals(esperado, resultado);        // Tenta procurar algo que não existe;
    }

    /**
     * Teste do método altura, da classe ArvoreAVL.
     */
    @Test
    public void testAltura() {
        String chave = "anesio";
        Palavra anesio = new Palavra(chave);
        ArvoreAVL tree = new ArvoreAVL();
        tree.inserir(anesio);                 // Insere um nó na raiz;

        int esperado = 1;
        int resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura da raiz;
        
        Palavra beatriz = new Palavra("beatriz");
        tree.inserir(beatriz);             // Insere mais um nó na raiz;
        esperado = 1;
        resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura;

        Palavra gustavo = new Palavra("gustavo");
        Palavra oswaldo = new Palavra("oswaldo");
        Palavra camila = new Palavra("camila");
        
        tree.inserir(gustavo);
        tree.inserir(oswaldo);
        tree.inserir(camila);              // Insere mais 3 nós na raiz e verifica sua altura (número de arestas entre a raiz e um nó folha);
        esperado = 2;
        resultado = tree.altura();
        assertSame(esperado, resultado);     // Verifica a altura;
    }

    /**
     * Teste do método tamanho, da classe ArvoreAVL.
     */
    @Test
    public void testTamanho() {
        ArvoreAVL tree = new ArvoreAVL();

        int esperado = 0;
        int resultado = tree.tamanho();
        assertSame(esperado, resultado);
        
        Palavra anesio = new Palavra("anesio");
        tree.inserir(anesio);             // Insere nós na árvore;
        esperado = 1;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // Verifica o tamanho da árvore;

        
        Palavra gustavo = new Palavra("gustavo");
        Palavra maria = new Palavra("maria");
        Palavra joao = new Palavra("joao");
        Palavra pedro = new Palavra("pedro");
        Palavra gabriel = new Palavra("gabriel");
        Palavra marcus = new Palavra("marcus");
        Palavra lucas = new Palavra("lucas");
        Palavra levi = new Palavra("levi");
        
        Palavra[] pessoas = new Palavra[]{gustavo, maria, joao, pedro, gabriel, marcus, lucas, levi};

        for (Palavra palavra : pessoas) {
            tree.inserir(palavra);          // Insere nós na árvore;
        }

        esperado = 9;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // Verifica o tamanho da árvore;

        tree.remover(pedro);
        tree.remover(joao);               // Remove dois nós da árvore;
        esperado = 7;
        resultado = tree.tamanho();
        assertSame(esperado, resultado);    // E verifica a contagem de nós;

        for (Palavra pessoa : pessoas) {
            tree.remover(pessoa);
        }
        tree.remover(anesio);
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
        ArvoreAVL tree = new ArvoreAVL();

        boolean esperado = true;
        boolean resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Nada é inserido na árvore, então é verificado se está vazia;
        
        Palavra chaves = new Palavra("chaves");
        Palavra chiquinha = new Palavra("chiquinha");
        Palavra kiko = new Palavra("kiko");
        
        Palavra[] vila = new Palavra[]{chaves, chiquinha, kiko};   // É criado um vetor de palavras para auxiliar no preenchimento de dados na árvore;

        for (Palavra personagem : vila) {
            tree.inserir(personagem);                                  // Os dados do vetor são inseridos na árvore;
        }
        esperado = false;
        resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Verifica se a árvore está vazia;

        for (Palavra personagem : vila) {
            tree.remover(personagem);                                  // Todas as palavras ão removidas da árvore;
        }
        esperado = true;
        resultado = tree.isEmpty();
        assertEquals(esperado, resultado);                             // Verifica se a árvore está vazia;
    }

    /**
     * Teste do método encontrar, da classe ArvoreAVL.
     */
    @Test
    public void testEncontrar() {
        ArvoreAVL tree = new ArvoreAVL();
        String chave = "anesio";
        Palavra anesio = new Palavra("anesio");
        tree.inserir(anesio);

        String esperado = "anesio";
        Node resultado = tree.encontrar(anesio);
        assertEquals(esperado, resultado.getKey().getChave());
        
        Palavra chaves = new Palavra("chaves");
        Palavra chiquinha = new Palavra("chiquinha");
        Palavra kiko = new Palavra("kiko");
        
        Palavra[] vila = new Palavra[]{chaves, chiquinha, kiko};

        for (Palavra personagem : vila) {
            tree.inserir(personagem);
        }

        chave = "kiko";
        esperado = "kiko";
        resultado = tree.encontrar(kiko);
        assertEquals(esperado, resultado.getKey().getChave());
    }

    /**
     * Teste do método getRaiz, da classe ArvoreAVL.
     */
    @Test
    public void testGetRaiz() {
        ArvoreAVL tree = new ArvoreAVL();

        String esperado = null;                                        // Nada é inserido na árvore;
        Node resultado = tree.getRaiz();
        assertEquals(esperado, resultado);                             // Então é verificado se a raiz possui null;
        
        Palavra anesio = new Palavra("anesio");
        tree.inserir(anesio);                                        // É inserido um nó, e como ele é o primeiro, ele passa a ser a raiz;
        esperado = "anesio";
        resultado = tree.getRaiz();
        assertEquals(esperado, resultado.getKey().getChave());                   // É verificado o estado da raiz;
        
        Palavra beatriz = new Palavra("beatriz");
        tree.inserir(beatriz);
        Palavra carlos = new Palavra("carlos");
        tree.inserir(carlos);                                        // São inseridos mais dois nós, e o valor da raiz muda por causa das rotações;
        esperado = "beatriz";
        resultado = tree.getRaiz();
        assertEquals(esperado, resultado.getKey().getChave());                   // É verificado o estado da raiz;

    }

    /**
     * Teste do método iterator, da classe ArvoreAVL.
     */
    @Test
    public void testIterator() {
        ArvoreAVL tree = new ArvoreAVL();
        
        tree.inserir(new Palavra("Naruto"));
        tree.inserir(new Palavra("Sasuke"));
        tree.inserir(new Palavra("Tsunade"));
        tree.inserir(new Palavra("Akatsuki"));

        String[] vetor = new String[4];

        int i = 0;
        Iterator<Palavra> itr = tree.iterator();
        while (itr.hasNext()) {
            Palavra n = itr.next();
            vetor[i] = n.getChave();
            i++;
        }

        assertEquals(vetor[0], "Akatsuki");
        assertEquals(vetor[1], "Naruto");
        assertEquals(vetor[2], "Sasuke");
        assertEquals(vetor[3], "Tsunade");

    }
}
