/* Livro: Estruturas de dados e algoritmos em Java
 * Página: 337, 342
 */
package util;

/**
 *
 * @author Anésio
 */
public class ArvoreAVL {

    private Node raiz;
    private int tam = 0;

    public class Node {
        String dado;
        int altura;
        int fator;
        Node esquerda;
        Node direita;

        public Node(String data){dado = data; altura = 1;}
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

        @Override
        public String toString() {
            return "Node{" + "Dado=" + dado + '}';
        }
        
    }
    // Não permite palavras repetidas
    public boolean inserir(String chave) {
        if (chave == null) {
            return false;
        }
        if (!contains(raiz, chave)) {
            raiz = inserir(raiz, chave);
            tam++;
            return true;
        }
        return false;
    }

    private Node inserir(Node node, String chave) {
        if (node == null) {
            return new Node(chave); // Caso base
        }
        int resultado = chave.compareTo(node.getDado());

        if (resultado < 0) {  // Vai pra esquerda?
            node.setEsquerda(inserir(node.getEsquerda(), chave));
        } else {
            node.setDireita(inserir(node.getDireita(), chave));
        }

        atualizar(node);
        return balancear(node);
    }
    
    /**
     * Método que remove uma chave da árvore, se a chave estiver na árvore. Esse método chama um método recursivo privado que faz o trabalho de remoção.
     *
     * @param chave dado a ser removido;
     * @return
     */
    public boolean remover(String chave) {
        if (chave == null) {
            return false;
        }

        if (contains(raiz, chave)) {
            raiz = remover(raiz, chave);
            tam--;
            return true;
        }
        return false;
    }

    private Node remover(Node node, String chave) {
        if (node == null) {
            return null;
        }

        int resultado = chave.compareTo(node.getDado());

        if (resultado < 0) { // então quer dizer que o valor que procuramos está do lado esquerdo
            node.setEsquerda(remover(node.getEsquerda(), chave));
        } else if (resultado > 0) { // então quer dizer que o valor que procuramos está do lado direito
            node.setDireita(remover(node.getDireita(), chave));
        } else { // quer dizer achamos o valor
            if (node.getEsquerda() == null) { // se ele so tiver filho a direita
                return node.getDireita();
            } else if (node.getDireita() == null) { // se ele so tiver filho a esquerda
                return node.getEsquerda();
            } else {  // se ele tiver ambos os filhos
                String valorDoSucessor = encontrarValorMin(node.getEsquerda());
                node.setDado(valorDoSucessor);
                node.setEsquerda(remover(node.getEsquerda(), valorDoSucessor));
            }
        }

        atualizar(node);

        return balancear(node);
    }
    
    /**
     * Método que encontra um nó, dada uma chave. Esse método chama um método privado que percorre a árvore e retorna a informação da existência ou não de um nó que contém essa chave.
     *
     * @param chave informação a ser buscada.
     * @return true ou false - Se for encontrada a informação, retorna true, se não, false.
     */
    public boolean contains(String chave) {
        return contains(raiz, chave);
    }

    private boolean contains(Node node, String chave) {
        if (node == null) {
            return false; // Caso base
        }
        int comparacao = chave.compareTo(node.dado);
        if (comparacao < 0) {
            return contains(node.getEsquerda(), chave);
        }
        if (comparacao > 0) {
            return contains(node.getDireita(), chave);
        }

        return true;
    }
    
    /**
     * Método que atualiza a altura e o fator de balanceamento de um nó.
     *
     * @param node nó a ser atualizado.
     */
    private void atualizar(Node node) {
        int alturaNoEsquerdo = (node.getEsquerda() == null) ? -1 : node.getEsquerda().getAltura();
        int alturaNoDireito = (node.getDireita() == null) ? -1 : node.getDireita().getAltura();

        node.setAltura(1 + Math.max(alturaNoEsquerdo, alturaNoDireito));

        node.setFator(alturaNoDireito - alturaNoEsquerdo);
    }
    
    /**
     * Método que rebalanceia o fator de balanceamento de um nó, caso ele seja +2 ou -2. 
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
                return rotacaoEsquerdaEsquerda(node);
            } else {
                return rotacaoEsquerdaDireita(node);
            }
        } else if (node.getFator() == +2) {
            if (node.getDireita().getFator() >= 0) {
                return rotacaoDireitaDireita(node);
            } else {
                return rotacaoDireitaEsquerda(node);
            }
        }
        return node;
    }

    private Node rotacaoEsquerdaEsquerda(Node node) {
        return rotacaoDireitaDireita(node);
    }

    private Node rotacaoEsquerdaDireita(Node node) {
        node.setEsquerda(rotacaoEsquerda(node.getEsquerda()));
        return rotacaoEsquerdaEsquerda(node);
    }

    private Node rotacaoDireitaDireita(Node node) {
        return rotacaoEsquerda(node);
    }

    private Node rotacaoDireitaEsquerda(Node node) {
        node.setDireita(rotacaoDireita(node.getDireita()));
        return rotacaoDireitaDireita(node);
    }

    private Node rotacaoEsquerda(Node node) {
        Node novoPai = node.getDireita();
        node.setDireita(novoPai.getEsquerda());

        novoPai.setEsquerda(node);
        atualizar(node);
        atualizar(novoPai);

        return novoPai;
    }

    private Node rotacaoDireita(Node node) {
        Node novoPai = node.getEsquerda();
        node.setEsquerda(novoPai.getDireita());

        novoPai.setDireita(node);
        atualizar(node);
        atualizar(novoPai);

        return novoPai;
    }

    private String encontrarValorMin(Node node) {
        while (node.getEsquerda() != null) {
            node = node.getEsquerda();
        }

        return node.getDado();
    }
    
    public int altura() {
        if (raiz == null) {
            return 0;
        }
        return raiz.getAltura();
    }

    public int tamanho() {
        return tam;
    }

    public boolean isEmpty() {
        return tamanho() == 0;
    }

    public Node getRaiz() {
        return raiz;
    }
}
