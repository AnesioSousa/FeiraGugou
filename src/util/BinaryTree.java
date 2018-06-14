/* Essa classe em sua grande maioria, foi criada pelo professor de estrutura de dados João Batista da Rocha Junior.
 * Somente fiz modificações que julguei necessárias.
 */
package util;

/**
 *
 * @author Anésio
 */
public class BinaryTree {
    private Node root;
    private int size;
    
    /**
     * Classe responsável por criar elementos da árvore.
     * 
     */
    private class Node  implements Element{
        private Object data; // Dado do nó;
        private Node parent; // Referencia para o pai;
        private Node left; // Referencia para a sub-árvore da esquerda;
        private Node right; // Referencia para a sub-árvore da direita;
        
        /**
         * Construtor da classe node que recebe um nó pai e um dado, atribuindo por fim ao novo nó.
         * 
         * @param parent - nó que é pai desse novo nó.
         * @param data - dado que será armazenado nesse nó.
         */
        public Node(Node parent, Object data){
            this.parent = parent;
            this.data = data;
        }
        
        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
        /**
         * Método que permite a manipulação de nós da árvore sem ter acesso a classe nó.
         * @return 
         */
        @Override
        public Object getData() {
            
            return null;
        }
        
        public void setData(Object data){
            this.data = data;
        }
        
    }
    /**
     * Método que adiciona uma raiz para a árvore se ela ainda não existir.
     * Esse método recebe um dado candidato a raiz de uma árvore. Se na árvore
     * já existe uma raiz, nada é feito. Se não, é criado um novo nó. Ao campo
     * de pai desse novo nó é dito null (já que ele será o nó raiz) e ao campo
     * de dados é adicionado o dado candidado a raiz.
     * @param data - dado candidato a ser o dado raiz.
     */    
    public void addRoot(Object data){
        if(root == null)
            root = new Node(null, data);
    }
    /**
     * Método que ao ser chamado, retorna o nó raiz ao chamador.
     * @return nó raiz.
     */
    public Element getRoot(){
        return root;
    }
    /**
     * Método que adiciona um nó a esquerda da árvore.
     * Esse método recebe por parâmetros o elemento a ser inserido,
     * e os dados que ele deverá armazenar. Esse elemento é do tipo
     * da interface "Element", então é necessária ser feita uma conversão
     * do tipo Element para o tipo Node da árvore.
     * 
     * @param elemento - elemento a ser adicionado na lista.
     * @param data - dado a ser adicionado no novo nó.
     */
    public void addLeft(Element elemento, Object data){    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< REVER ISSSOOO!!!!!!!!
        Node e = (Node) elemento;
        e.setLeft(new Node(e, data));
        size++;
    }
    /**
     * Método que adiciona um nó a direita da árvore.
     * Esse método recebe por parâmetros o elemento a ser inserido,
     * e os dados que ele deverá armazenar. Esse elemento é do tipo
     * da interface "Element", então é necessária ser feita uma conversão
     * do tipo Element para o tipo Node da árvore.
     * 
     * @param elemento - elemento a ser adicionado na lista.
     * @param data - dado a ser adicionado no novo nó.
     */
    public void addRight(Element elemento, Object data){ // <<<<<<<<<<<<<<<<<<<<<<<< REVER ISSOOOO !!!!
        ((Node)elemento).setLeft(new Node((Node) elemento, data));
    }
    //<<<<<<<<<<<<<<<<<<<<<< REVER ESSA DESCRIÇÃO!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * Método que retorna um nó da esquerda, dado um determinado pai. 
     * @param elemento - nó pai.
     * @return filho esquerdo do nó pai recebido por parâmetro.
     */
    public Element getLeft(Element elemento){
        return ((Node) elemento).getLeft();
    }
    //<<<<<<<<<<<<<<<<<<<<<< REVER ESSA DESCRIÇÃO!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * Método que retorna um nó da direita, dado um determinado pai. 
     * @param elemento - nó pai.
     * @return filho direito do nó pai recebido por parâmetro.
     */
    public Element getRight(Element elemento){
        return ((Node) elemento).getRight();
    }
    //<<<<<<<<<<<<<<<<<<<<<< REVER ESSA DESCRIÇÃO!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * Método que retorna o pai de um nó recebido.
     * @param elemento - nó recebido.
     * @return return nó pai do nó recebido por parâmetro.
     */
    public Element getParent(Element elemento){
        return ((Node) elemento).getParent();
    }
    //<<<<<<<<<<<<<<<<<<<<<< REVER ESSA DESCRIÇÃO!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * Método que define um dado para um determinado nó.
     * @param elemento - nó a que terá seus dados definidos.   
     * @param data - dado a ser definido para o nó.
     */
    public void set(Element elemento, Object data){
        ((Node) elemento).setData(data);
    }
    /**
     * 
     * @param e 
     */
    public void remove(Element e){}
    public int size(){}
    public int height (Element e){}
    public Iterator iterator(){} 
    
}
