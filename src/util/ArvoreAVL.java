/* Essa árvore foi criada por William Fiset. Fiz alterações e adicionei métodos para satisfazer a resolução do problema.
 * O código dele pode ser encontrado em: <https://github.com/williamfiset/data-structures/blob/master/com/williamfiset/datastructures/balancedtree/AVLTreeRecursive.java>
 * Segue o vídeo onde ele fala sobre a árvore: <https://www.youtube.com/watch?v=tqFZzXkbbGY> acessado em: 17 junho 2018
 */
package util;

import model.Palavra;

/**
 * Classe que gera objetos do tipo Árvore Binária de Busca Balanceada
 * @author William Fiset - 80 % do código. 
 * @author Anésio Sousa - 20 % do código.
 */
public class ArvoreAVL {

    private Node raiz;
    private int tam = 0;
    
    /**
     * Classe que cria objetos que são nós de árvores avl.
     *
     * @author Anésio Sousa
     */
    public class Node {

        private Palavra key;
        private int altura;
        private int fator;
        private Node esquerda;
        private Node direita;

        /**
         * Construtor da classe Node. Recebe um objeto palavra para ser seu dado key, e inicializa os atributos da classe Node.
         * @param data dado para ser a key do nó criado.
         */
        public Node(Palavra data) {
            key = data;
            altura = 1;
        }

        /**
         * Retorna a informação guardada nesse nó.
         * @return objeto Palavra contendo a informação.
         */
        public Palavra getKey() {
            return key;
        }

        /**
         * Dita qual informação esse nó irá passar guardar.
         * @param key objeto Palavra contendo informação.
         */
        public void setKey(Palavra key) {
            this.key = key;
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
    }
    /**
     * Insere o elemento especificado nessa árvore.
     * Método público de inserção de elementos. Esse método chama um método privado que faz a inserção do elemento.
     * @param chave elemento a ser inserido.
     * @return true se o elemento for diferente de nulo e se o ele já não estiver na lista. false caso contrário.  
     */
    public boolean inserir(Palavra chave) {
        if (chave == null) {
            return false;
        }
        if (!contains(raiz, chave)) {
            tam++;
            raiz = inserir(raiz, chave);
            return true;
        }
        return false;
    }
    /**
     * Insere o elemento especificado nessa árvore.
     * Método privado de inserção de elementos. Esse método procura um espaço vazio para inserir um novo elemento na árvore, fazendo comparações
     * que determinam o local onde o novo elemento deverá ficar. Após o elemento estar no seu local, a altura e o fator de balanceamento dele é  
     * atualizado. O método recebe a raiz da árvore pois ela é o ponto de partida para qualquer local da árvore.
     * @param node raiz da árvore.
     * @param word elemento a ser inserido.
     * @return o chamado da função de balanceamento passando esse elemento.
     */
    private Node inserir(Node node, Palavra word) {
        if (node == null) {
            return new Node(word); 
        }
        int resultado = word.getChave().compareToIgnoreCase(node.getKey().getChave());

        if (resultado < 0) { 
            node.setEsquerda(inserir(node.getEsquerda(), word));
        } else {
            node.setDireita(inserir(node.getDireita(), word));
        }
        
        if(tamanho() > 2)
            atualizar(node);
        
        return balancear(node);
    }
    
    /**
     * Remove o elemento especificado desta árvore, caso ele esteja na árvore. 
     * Método publico de remoção de elementos. Esse método chama um método privado que faz a remoção do elemento.
     *
     * @param word dado a ser removido;
     * @return true se o elemento for diferente de nulo e se o ele estiver na lista. false caso contrário.  
     */
    public boolean remover(Palavra word) {
        if (word == null) {
            return false;
        }

        if (contains(raiz, word)) {
            raiz = remover(raiz, word);
            tam--;
            return true;
        }
        return false;
    }
    /**
     * Remove o elemento especificado desta árvore, caso ele esteja na árvore. 
     * Método privado de remoção de elementos. Esse método percorre a árvore em busca do elemento a ser removido.
     * Quando o elemento é encontrado, é verificado se ele é um nó folha, se ele é um nó pai de um filho, ou se é
     * um nó pai de dois filhos. Após a verificação, é feito o trabalho de substituição de dados de acordo a característica
     * do nó. Após a substituição, a altura e o fator de balanceamento do nó é atualizado. O método recebe a raiz da árvore
     * pois ela é o ponto de partida para qualquer local da árvore.
     * @param node raiz da árvore.
     * @param word elemento a ser removido.
     * @return o chamado da função de balanceamento passando esse elemento.
     */
    private Node remover(Node node, Palavra word) {
        if (node == null) {
            return null;
        }

        int resultado = word.getChave().compareToIgnoreCase(node.getKey().getChave());

        if (resultado < 0) {
            node.setEsquerda(remover(node.getEsquerda(), word));
        } else if (resultado > 0) {
            node.setDireita(remover(node.getDireita(), word));
        } else {
            if (node.getEsquerda() == null) {
                return node.getDireita();
            } else if (node.getDireita() == null) {
                return node.getEsquerda();
            } else {
                Palavra valorDoSucessor = encontrarValorMax(node.getEsquerda());
                node.setKey(valorDoSucessor);
                node.setEsquerda(remover(node.getEsquerda(), valorDoSucessor));
            }
        }

        atualizar(node);

        return balancear(node);
    }
    
    /**
     * Verifica a existencia de um elemento nessa árvore.
     * Método público de verificação de existência. Esse método chama um método privado que percorre a árvore e retorna 
     * a informação da existência ou não desse elemento na árvore.
     *
     * @param chave elemento a ser buscado.
     * @return a chamada do método privado de verificação de existência.
     */
    public boolean contains(Palavra chave) {
        return contains(raiz, chave);
    }
    /**
     * Verifica a existencia de um elemento nessa árvore.
     * Método privado de verificação de existência. Esse método percorre a árvore em busca de um determinado elemento, retornando
     * a informação de se ele está contido ou não na árvore. O método recebe a raiz da árvore pois ela é o ponto de partida para 
     * qualquer local da árvore.
     * @param node raiz da árvore.
     * @param word elemento a ser procurado.
     * @return true ou false - Se for encontrada a informação, retorna true, se não, false.
     */
    private boolean contains(Node node, Palavra word) {
        if (node == null) {
            return false; // Caso base
        }
        int comparacao = word.getChave().compareToIgnoreCase(node.getKey().getChave());
        if (comparacao < 0) {
            return contains(node.getEsquerda(), word);
        }
        if (comparacao > 0) {
            return contains(node.getDireita(), word);
        }

        return true;
    }
    
    /**
     * Método que atualiza a altura e o fator de balanceamento de um nó.
     * @param node nó a ser atualizado.
     */
    private void atualizar(Node node) {
        int alturaNoEsquerdo = (node.getEsquerda() == null) ? -1 : node.getEsquerda().getAltura();
        int alturaNoDireito = (node.getDireita() == null) ? -1 : node.getDireita().getAltura();

        node.setAltura(1 + Math.max(alturaNoEsquerdo, alturaNoDireito));

        node.setFator(alturaNoDireito - alturaNoEsquerdo);
    }
    
    /**
     * Método que altera o fator de balanceamento de um nó, caso ele seja +2 ou -2. 
     * Se o fator for -2, quer dizer que a árvore está mais "pesada" para a esquerda. 
     * Se o fator for +2, quer dizer que a árvore está mais "pesada" para a direita. 
     * Se a árvore está "pesada", o método utiliza rotações simples e duplas para realizar o balanceamento. 
     * Se não, quer dizer que o fator é 0, +1 ou -1. Nesse caso não é necessário fazer nada.
     * @param node nó a ser rebalanceado.
     * @return node rebalaceado.
     */
    private Node balancear(Node node) {
        if (node.getFator() == -2) {
            if (node.getEsquerda().getFator() <= 0) {
                return rotacaoLL(node);
            } else {
                return rotacaoLR(node);
            }
        } else if (node.getFator() == +2) {
            if (node.getDireita().getFator() >= 0) {
                return rotacaoRR(node);
            } else {
                return rotacaoRL(node);
            }
        }
        return node;
    }
    
    private Node rotacaoLL(Node node) {
        return rotacaoDireita(node);
    }
    /**
     * Rotaciona duplamente: primeiro à direita, depois à esquerda.
     * @param node nó a ser rotacionado.
     * @return chamada para rotação a direita.
     */
    private Node rotacaoLR(Node node) {
        node.setEsquerda(rotacaoEsquerda(node.getEsquerda()));
        return rotacaoLL(node);
    }
    private Node rotacaoRR(Node node) {
        return rotacaoEsquerda(node);
    }
    /**
     * Rotaciona duplamente: primeiro à esquerda, depois à direita.
     * @param node nó a ser rotacionado.
     * @return chamada para rotação a esquerda.
     */
    private Node rotacaoRL(Node node) {
        node.setDireita(rotacaoDireita(node.getDireita()));
        return rotacaoRR(node);
    }
    /**
     * Rotaciona um nó à esquerda quando a árvore está "pesada" para a direita.
     * @param node nó a ser rotacionado.
     * @return nó rotacionado.
     */
    private Node rotacaoEsquerda(Node node) {
        Node novoPai = node.getDireita();
        node.setDireita(novoPai.getEsquerda());

        novoPai.setEsquerda(node);
        atualizar(node);
        atualizar(novoPai);

        return novoPai;
    }
    /**
     * Rotaciona um nó à direita quando a árvore está "pesada" para a esquerda.
     * @param node nó a ser rotacionado.
     * @return nó rotacionado.
     */
    private Node rotacaoDireita(Node node) {
        Node novoPai = node.getEsquerda();
        node.setEsquerda(novoPai.getDireita());

        novoPai.setDireita(node);
        atualizar(node);
        atualizar(novoPai);

        return novoPai;
    }
    /**
     * Encontra o proximo maior nó (que tem o maior valor - sucessor).
     * @param node maior nó anterior.
     * @return dado do nó sucessor.
     */
    private Palavra encontrarValorMax(Node node) {
        while (node.getDireita()!= null) {
            node = node.getDireita();
        }

        return node.getKey();
    }
    
    /**
     * Retorna a altura da raiz.
     * @return 0 se a raiz for nula, caso contrário retorna a altura da raiz.
     */
    public int altura() {
        if (raiz == null) {
            return 0;
        }
        return raiz.getAltura();
    }
    /**
     * Procura o nó que armazena um elemento.
     * @param word elemento a ser procurado.
     * @return nó caso ele seja entrado, e null caso contrário.
     */
    public Node encontrar(Palavra word) {  
        Node atual = raiz;
        while(atual != null){
            if(word.compareTo(atual.getKey()) < 0){
                atual = atual.getEsquerda();
            }
            else if(word.compareTo(atual.getKey()) > 0){
                atual = atual.getDireita();
            }else
                return atual;
        }
        return null;
    }
    
    /**
     * Retorna o tamanho desta árvore.
     * @return int com o valor do tamanho.
     */
    public int tamanho() {
        return tam;
    }

    /**
     * Retorna a informação de se esta árvore está vazia.
     * @return true ou false - depende de se a árvore tem nós ou não.
     */
    public boolean isEmpty() {
        return tamanho() == 0;
    }

    /**
     * Retorna a raiz desta árvore.
     * @return nó raiz.
     */
    public Node getRaiz() {
        return raiz;
    }

    /**
     * Retorna um iterador para percorrer a árvore em ordem.
     * @return iterador.
     */
    public java.util.Iterator<Node> iterator () {

        final int expectedNodeCount = tam;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(raiz);

        return new java.util.Iterator<Node> () {
            Node trav = raiz;
            @Override 
            public boolean hasNext() {
                if (expectedNodeCount != tam) throw new java.util.ConcurrentModificationException();        
                return raiz != null && !stack.isEmpty();
            }
            @Override 
            public Node next () {
                if (expectedNodeCount != tam) throw new java.util.ConcurrentModificationException();

                while(trav != null && trav.getEsquerda() != null) {
                    stack.push(trav.getEsquerda());
                    trav = trav.getEsquerda();
                }

                Node node = stack.pop();

                if (node.getDireita() != null) {
                    stack.push(node.getDireita());
                    trav = node.getDireita();
                }

                return node;
            }      
        };
    }
}
