/* Livro: Estruturas de dados e algoritmos em Java
 * Página: 336
 */
package util;
/**
 *
 * @author Anésio
 */
public class Node {
    String dado;
    int altura;
    Node filhoDaEsquerda;
    Node filhoDaDireita;
    
    public Node(String data){dado = data; altura = 1;}
    public String getDado() {return dado;}
    public int getAltura() {return altura;}
    public void setAltura(int altura) {this.altura = altura;}
    public Node getFilhoDaEsquerda() {return filhoDaEsquerda;}
    public void setFilhoDaEsquerda(Node filhoDaEsquerda) {this.filhoDaEsquerda = filhoDaEsquerda;}
    public Node getFilhoDaDireita() {return filhoDaDireita;}
    public void setFilhoDaDireita(Node filhoDaDireita) {this.filhoDaDireita = filhoDaDireita;}
    
    
    
    @Override
    public String toString() {
        return "Node{" + "Dado=" + dado + '}';
    }
    
    
    
}
