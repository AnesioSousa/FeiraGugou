package util;

import java.util.ArrayList;
import java.util.Iterator;
import model.Dados;

/**
 *
 * @author anesio
 */
public class Node{
        private String chave;
        private int altura;
        private int fator;
        private Node esquerda;
        private Node direita;
        private int vezesBuscada;
        private ArrayList<Dados> dados;

        public Node(String data){
            chave = data;
            altura = 1;
            dados = new ArrayList<>();
            vezesBuscada = 0;
        }

        public String getChave() {return chave;}
        public void setChave(String chave) {this.chave = chave;}
        public int getAltura() {return altura;}
        public void setAltura(int altura) {this.altura = altura;}
        public int getFator() { return fator;}
        public void setFator(int fator) {this.fator = fator;}
        public Node getEsquerda() {return esquerda;}
        public void setEsquerda(Node esquerda) {this.esquerda = esquerda;}
        public Node getDireita() {return direita;}
        public void setDireita(Node direita) {this.direita = direita;}
        public int getVezesBuscada() {return vezesBuscada;}
        public void incrementVezesBuscada() {this.vezesBuscada++;}
        public ArrayList<Dados> getListaDados() {return dados;}
        public Iterator listarDados(){return dados.iterator();}
        
        @Override
        public String toString() {
            return chave;
        }
        
    }