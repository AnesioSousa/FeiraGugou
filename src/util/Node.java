package util;

import java.util.ArrayList;
import java.util.Iterator;
import model.Dados;

/**
 * Classe que cria objetos que são nós de árvores avl.
 *
 * @author Anésio Sousa
 */
public class Node {

    private String chave;
    private int altura;
    private int fator;
    private Node esquerda;
    private Node direita;
    private int vezesBuscada;
    private ArrayList<Dados> dados;

    /**
     * Construtor da classe Node. Recebe uma string para ser seu dado chave, e inicializa os atributos da classe Node.
     *
     * @param data dado para ser a chave do nó criado.
     */
    public Node(String data) {
        chave = data;
        altura = 1;
        dados = new ArrayList<>();
        vezesBuscada = 0;
    }

    /**
     * Retorna a informação guardada nesse nó.
     * @return string contendo a informação.
     */
    public String getChave() {
        return chave;
    }

    /**
     * Dita qual informação esse nó irá passar guardar.
     * @param chave string contendo informação.
     */
    public void setChave(String chave) {
        this.chave = chave;
    }

    /**
     * Retorna a altura desse nó.
     * @return variavel int contendo a altura.
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Dita qual será a altura desse nó.
     * @param altura nova altura.
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Retorna o fator desse nó.
     * @return variavel int contendo o fator.
     */
    public int getFator() {
        return fator;
    }

    /**
     * Dita qual será o fator desse nó.
     * @param fator novo fator.
     */
    public void setFator(int fator) {
        this.fator = fator;
    }

    /**
     * Retorna o nó a esquerda deste nó.
     * @return nó a esquerda.
     */
    public Node getEsquerda() {
        return esquerda;
    }

    /**
     * Dita qual será o nó da esquerda deste nó.
     * @param esquerda novo nó da esquerda.
     */
    public void setEsquerda(Node esquerda) {
        this.esquerda = esquerda;
    }

    /**
     * Retorna o nó da direita deste nó.
     * @return nó da direita.
     */
    public Node getDireita() {
        return direita;
    }

    /**
     * Dita qual será o nó da direita deste nó.
     * @param direita novo nó da direita.
     */
    public void setDireita(Node direita) {
        this.direita = direita;
    }

    /**
     * Retorna a quantidade de vezes que a informação desse nó foi buscada.
     * @return int contendo a quantidade.
     */
    public int getVezesBuscada() {
        return vezesBuscada;
    }

    /**
     * Incrementa o contador de quantidade de vezes que informação desse nó foi buscada.
     */
    public void incrementVezesBuscada() {
        this.vezesBuscada++;
    }

    /**
     * Retorna a lista de dados desse nó.
     * @return lista de dados.
     */
    public ArrayList<Dados> getListaDados() {
        return dados;
    }

    /**
     * Retorna o iterador da lista de dados desse nó.
     * @return iterador da lista.
     */
    public Iterator listarDados() {
        return dados.iterator();
    }

    @Override
    public String toString() {
        return chave;
    }
}
