package util;

import java.util.ArrayList;
import java.util.Iterator;
import model.Pagina;

/**
 *
 * @author anesio
 */
public class Node{
        private String dado;
        private int altura;
        private int fator;
        private Node esquerda;
        private Node direita;
        private int vezesBuscada;
        private ArrayList<Pagina> paginas;

        public Node(String data){
            dado = data;
            altura = 1;
            paginas = new ArrayList<>();
            vezesBuscada = 0;
        }

        public String getDado() {return dado;}
        public void setDado(String dado) {this.dado = dado;}
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
        public ArrayList<Pagina> getPaginas() {return paginas;}
        public Iterator listarPaginas(){return paginas.iterator();}
        
        @Override
        public String toString() {
            return dado;
        }
        
    }