package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe responsável por criar objetos que serão os dados armazenados nos nós da árvore.
 * A classe implementa a interface Comparable para que seus objetos se tenham uma ordem natural e se tornem comparáveis.
 * @author Anésio Sousa
 */
public class Palavra implements Comparable<Palavra>{
    private String chave;
    private int vezesBuscada;
    private ArrayList<Dados> dados;

    /**
     * Primeiro construtor da classe Palavra, nele são inicializados os atributos da classe.
     * @param chave string inicial para o objeto.
     */
    public Palavra(String chave){
        this.chave = chave;
        this.vezesBuscada = 0;
        dados = new ArrayList<>(); 
    }

    /**
     * Segundo construtor da classe Palavra, nele são inicializados os atributos da classe.
     */
    public Palavra() {
        this.vezesBuscada = 0;
        dados = new ArrayList<>();
    }

    /**
     * Retorna a chave que palavra armazena.
     * @return string que armazena a chave.
     */
    public String getChave() {
        return chave;
    }

    /**
     * Dita qual chave essa palavra irá passar a ter.
     * @param chave string contendo a chave.
     */
    public void setChave(String chave) {
        this.chave = chave;
    }

    /**
     * Retorna a quantidade de vezes que a chave desse objeto palavra foi buscada.
     * @return int contendo a quantidade.
     */
    public int getVezesBuscada() {
        return vezesBuscada;
    }

    /**
     * Incrementa o contador de vezes que a chave desse objeto palavra foi buscada.
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Palavra) {
            Palavra p = (Palavra) obj;
            
            if(this.getChave().equals(p.getChave())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return chave;
    }

    @Override
    public int compareTo(Palavra o) {
        return this.getChave().compareToIgnoreCase(o.getChave());
    }
}
