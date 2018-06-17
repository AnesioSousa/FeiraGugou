/* Livro: Estruturas de dados e algoritmos em Java
 * Página: 337, 342
 */
package util;

/**
 *
 * @author Anésio
 */
public class Tree {

    private Node raiz;
    private int tam = 0;

    public void inserir(String chave) {
        this.raiz = inserir(this.raiz, chave);
    }

    private Node inserir(Node node, String chave) {
        if(node == null) { // Verifica se na raiz tem algum nó;  
            Node novo = new Node(chave);
            return novo;
        }

        int resultado = chave.compareTo(node.getDado());
        
        if(resultado < 0) {  // Vai pra esquerda?
            node.setEsquerda(inserir(node.getEsquerda(), chave));
        } else {              // Ou pra direita? <<< REVER SE PRECISA DE else if resultado > 0 e else if resultado == 0 >>>>>
            node.setDireita(inserir(node.getDireita(), chave));
        }

        node.setAltura(altura(node));

        int fator = diferencaDeAltura(node);

        // Rotação a direita - RR
        if(fator > 1 && chave.compareTo(node.getEsquerda().getDado()) < 0) {
            return rotacaoADireita(node);
        }

        // Rotação a esquerda - LL
        if(fator < -1 && chave.compareTo(node.getDireita().getDado()) > 0) {
            return rotacaoAEsquerda(node);
        }
        
        // Rotação a esquerda e direita - LR
        if(fator > 1 && chave.compareTo(node.getEsquerda().getDado()) > 0){
            node.setEsquerda(rotacaoAEsquerda(node.getEsquerda()));
            return rotacaoADireita(node);
        }
            
        // Rotação a direita e esquerda - RL
        if(fator < -1 && chave.compareTo(node.getDireita().getDado()) < 0){
            node.setDireita(rotacaoADireita(node.getDireita()));
            return rotacaoAEsquerda(node);
        }
        
        return node;
    }

    //<<<<<<<<<<<<<<< VERIFICAR SE ESSE "ehFilhoEsquerdo" é realmente necessário >>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<< TENTAR UTILIZAR O MÉTODO "encontrar" PRA LOCALIZAR O NÓ A SER REMOVIDO >>>>>>>>>>>>>>>>S
    public boolean remover(String chave) {
        Node atual = raiz;
        Node pai = raiz;
        boolean ehFilhoEsquerdo = true;
        
        while(!atual.getDado().equals(chave)){
            pai = atual;
            if(chave.compareTo(atual.getDado()) < 0){
                ehFilhoEsquerdo = true;
                atual = atual.getEsquerda();
            }
            else{
                ehFilhoEsquerdo = false;
                atual = atual.getDireita();
            }
            
            if(atual == null)
                return false;
        }
        
        // Se chegou aqui é pq achou o nó a ser retirado;
        
        // Se é um nó filho, simplesmente o remova (da árvore, fazendo com que seu pai aponte pra null. Ele ainda existe na memória até que o garbage collector o pegue).
        if(atual.getEsquerda() == null && atual.getDireita() == null){
            if(atual == raiz)
                raiz = null;
            else if(ehFilhoEsquerdo)
                pai.setEsquerda(null);
            else
                pai.setDireita(null);
        } 
        
        // SE O NÓ A SER REMOVIDO TEM UM FILHO
        // Se não há filho à direita, substitui por subárvore à esquerda
        else if(atual.getDireita() == null){
            if(atual == raiz)
                raiz = atual.getEsquerda();
            else if (ehFilhoEsquerdo) // filho à esquerda do pai
                pai.setEsquerda(atual.getEsquerda());
            else                      // filho à direita do pai
                pai.setDireita(atual.getEsquerda());
        }
        // Se não há filho à esquerda, substitui por subárvore à direita
        else if(atual.getEsquerda() == null){
           // UTILIZAR EQUALS
            if(atual == raiz)
                raiz = atual.getDireita();
            else if(ehFilhoEsquerdo)
                pai.setEsquerda(atual.getDireita());
            else
                pai.setDireita(atual.getDireita());
        }
        else{ // tem dois filhos;
            // Pegue o sucessor do nó a ser deletado (atual)
            Node sucessor = getSucessor(atual);
            
            if(atual == raiz){
                raiz = sucessor;
            }else if(ehFilhoEsquerdo){
                pai.setEsquerda(sucessor); 
            }else{
                pai.setDireita(sucessor);
            }
            // conecte sucessor ao filho à esquerda de atual.
            sucessor.setEsquerda(atual.getEsquerda());
        } // fim do else dois filhos
        //      (sucessor não pode ter filho à esquerda)
        return true;               // sucesso
    } // fim de delete()
    
    private Node getSucessor(Node delNode){
        Node sucessorPai = delNode;
        Node sucessor = delNode;
        Node atual = delNode.getDireita(); // vai para filho da direita
        
        while(atual != null){              // percorre até não ter mais filhos à esquerda
            sucessorPai = sucessor;
            sucessor = atual;
            atual = atual.getEsquerda(); // vai para o filho da esquerda
        }                                           // se o sucessor não é
                                                    // o filho da direita
        if(sucessor != delNode.getDireita()){       // faz conexões
            sucessorPai.setEsquerda(sucessor.getDireita());
            sucessor.setDireita(delNode.getDireita());
        }
        return sucessor;
    }
    
    private Node rotacaoADireita(Node c) {
        Node b = c.getEsquerda(); // Referencia T3
        Node T3 = b.getDireita(); // Referencia c

        // Rotação
        b.setDireita(c);
        c.setEsquerda(T3);

        // Atualização de altura
        c.setAltura(altura(c));
        b.setAltura(altura(b));

        return b;
    }

    private Node rotacaoAEsquerda(Node c) {
        Node b = c.getDireita();
        Node T2 = b.getEsquerda();

        // Rotação
        b.setEsquerda(c);
        c.setDireita(T2);

        // Atualização de altura
        c.setAltura(altura(c));
        b.setAltura(altura(b));

        return b;
    }
    
    /**
     * Método que encontra um nó, dada uma chave. 
     * Esse método percorre a árvore e retorna (se existir) um nó que contém essa chave.
     * @param chave - informação a ser buscada na árvore.
     * @return null ou um Node - se a árvore não conter a informação buscada, retorna null. Se conter, retorna o nó que a contém.
     */
    public Node encontrar(String chave) {
        Node atual = raiz;

        while (!atual.getDado().equals(chave)) { // Se tiver dúvidas, fazer teste de mesa utilizando a árvore da página 339
            if (chave.compareTo(atual.getDado()) < 0) {
                atual = atual.getEsquerda();
            }else{
                atual = atual.getDireita();
            }

            if (atual == null) { // Se for nulo, quer dizer que percorreu a árvore um nó que não tem mais filho,                 
                return null;  // ai recebeu nulo. Então, retorna nulo. De que não encontrou.
            }
        }
        return atual;
    }
    // vários outros métodos
    
    private int altura(Node no) {
        if (no == null) {
            return 0;
        } else {
            return Math.max(1 + altura(no.getEsquerda()),
                    1 + altura(no.getDireita()));
        }
        // SE ESTIVER DANDO ERROS, VERIFICAR AQUI. JOÃO POE 2 "1 + ..." E A MULHER DO VÍDEO POE SÓ 1( 1:48:51 O TEMPO DO VÍDEO)
    }

    /**
     * Método que dá o fator de balanceamento.
     *
     * @param no
     * @return
     */
    private int diferencaDeAltura(Node no) {
        if (no == null) {
            return 0;
        }

        return altura(no.getEsquerda()) - altura(no.getDireita());
    }
    
    //PERCORRER:
    /**
     *
     * @param localRaiz
     */
    public void emOrdem(Node localRaiz) {
        if (localRaiz != null) {
            emOrdem(localRaiz.getEsquerda());
            System.out.println(localRaiz.getDado() + " ");
            emOrdem(localRaiz.getDireita());
        }
    }
    // <<<<<<<<<<<< Ver se isso é realmente necessário >>>>>>>>>>>>

    /**
     *
     * @return
     */
    public Node minimo() {
        Node atual, ultimo = null;

        atual = raiz;
        while (atual != null) {
            ultimo = atual;
            atual = atual.getEsquerda();
        }
        return ultimo;
    }

    /**
     *
     * @return
     */
    public Node getRaiz() {
        return raiz;
    }
} // fim da classe Tree
