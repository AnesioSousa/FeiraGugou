package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author anesio
 */
public class Palavra implements Comparable<Palavra>{
    private String chave;
    private int vezesBuscada;
    private ArrayList<Dados> dados;

    public Palavra(String chave){
        this.chave = chave;
        this.vezesBuscada = 0;
        dados = new ArrayList<>(); 
    }
    public Palavra() {
        this.vezesBuscada = 0;
        dados = new ArrayList<>();
    }

    public String getChave() {
        return chave;
    }

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
