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
    Node esquerda;
    Node direita;
    
    public Node(String data){dado = data; altura = 1;}
    public String getDado() {return dado;}
    public int getAltura() {return altura;}
    public void setAltura(int altura) {this.altura = altura;}
    public Node getEsquerda() {return esquerda;}
    public void setEsquerda(Node esquerda) {this.esquerda = esquerda;}
    public Node getDireita() {return direita;}
    public void setDireita(Node direita) {this.direita = direita;}
    
    @Override
    public String toString() {
        return "Node{" + "Dado=" + dado + '}';
    }
    
    
    
}
