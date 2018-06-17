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
    private int size;
    
    
    // <<<<<<<<<<<<<<<<<<<<<<<<< REVER JAVADOC >>>>>>>>>>>>>>>>>
    /**
     * Método publico que insere um novo nó com dados na árvore.
     * Esse método recebe uma string como chave, cria um novo nó que contem essa chave, e tenta inserir o novo nó na árvore.
     * Se não tiver nós na árvore, esse novo nó vira a raiz. Se tiver, esse método percorre toda a árvore procurando um local
     * para posicionar corretamente esse novo nó.
     * 
     * @param chave - dado a ser armazenado na árvore.
     */
    public void inserir(String chave){
        this.raiz = inserir(this.raiz, chave);
    }
    
    private Node inserir(Node no, String chave) {
        if (no == null){ // Verifica se na raiz tem algum nó;  
            Node novo = new Node(chave);
            return novo;
        }
        
        int resultado = chave.compareTo(no.getDado());
        if(resultado < 0){  // Vai pra esquerda?
            no.setFilhoDaEsquerda(inserir(no.getFilhoDaEsquerda(), chave));
        }else{              // Ou pra direita?
            no.setFilhoDaDireita(inserir(no.getFilhoDaDireita(), chave));
        } 
        
        no.setAltura(altura(no));
        
        int fator = diferencaDeAltura(no);
        
        // Rotação a direita
        if(fator > 1 && chave.compareTo(no.getFilhoDaEsquerda().getDado()) < 0){
            return rotacaoADireita(no);
        }
        
        // Rotação a esquerda
        if(fator < -1 && chave.compareTo(no.getFilhoDaDireita().getDado()) > 0){
            return rotacaoAEsquerda(no);
        }
        
        return no;
    } 
    /**
     * Método necessário para verificar o balanceamento da árvore.
     * @param no
     * @return 
     */
    private int altura(Node no){
        if(no == null){
            return 0;
        }else{
            return Math.max(1 + altura(no.getFilhoDaEsquerda()), 
                            1 + altura(no.getFilhoDaDireita()));
        }
        // SE ESTIVER DANDO ERROS, VERIFICAR AQUI. JOÃO POE 2 "1 + ..." E A MULHER DO VÍDEO POE SÓ 1( 1:48:51 O TEMPO DO VÍDEO)
    }
    /**
     * Método que dá o fator de balanceamento.
     * 
     * @param no
     * @return 
     */
    private int diferencaDeAltura(Node no){
        if(no == null){
            return 0;
        }
        
        return altura(no.getFilhoDaEsquerda()) - altura(no.getFilhoDaDireita());
    }
            
    
    private Node rotacaoADireita(Node c){
        Node b = c.getFilhoDaEsquerda(); // Referencia T3
        Node T3 = b.getFilhoDaDireita(); // Referencia c
        
        // Rotação
        
        b.setFilhoDaDireita(c);
        c.setFilhoDaEsquerda(T3);
        
        // Atualização de altura
        
        c.setAltura(altura(c));
        b.setAltura(altura(b));
        
        return b;
    }
    
    private Node rotacaoAEsquerda(Node c){
        Node b = c.getFilhoDaDireita();
        Node T2 = b.getFilhoDaEsquerda();
        
        // Rotação
        
        b.setFilhoDaEsquerda(c);
        c.setFilhoDaDireita(T2);
        
        // Atualização de altura
        
        c.setAltura(altura(c));
        b.setAltura(altura(b));
        
        return b;
    }
    
    //<<<<<<<<<<<<<<< VERIFICAR SE ESSE "ehFilhoEsquerdo" é realmente necessário >>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*<<<<<<<<<<<<<<< TENTAR UTILIZAR O MÉTODO "encontrar" PRA LOCALIZAR O NÓ A SER REMOVIDO >>>>>>>>>>>>>>>>S
    public boolean deletar(String chave) {
        Node atual = raiz;
        Node pai = raiz;
        boolean ehFilhoEsquerdo = true;
        
        while(!atual.getDado().equals(chave)){
            pai = atual;
            if(chave.compareTo(atual.getDado()) < 0){
                ehFilhoEsquerdo = true;
                atual = atual.getFilhoDaEsquerda();
            }
            else{
                ehFilhoEsquerdo = false;
                atual = atual.getFilhoDaDireita();
            }
            
            if(atual == null)
                return false;
        }
        
        // Se chegou aqui é pq achou o nó a ser retirado;
        
        // Se é um nó filho, simplesmente o remova (da árvore, fazendo com que seu pai aponte pra null. Ele ainda existe na memória até que o garbage collector o pegue).
        if(atual.getFilhoDaEsquerda() == null && atual.getFilhoDaDireita() == null){
            if(atual == raiz)
                raiz = null;
            else if(ehFilhoEsquerdo)
                pai.setFilhoDaEsquerda(null);
            else
                pai.setFilhoDaDireita(null);
        } 
        
        // SE O NÓ A SER REMOVIDO TEM UM FILHO
        // Se não há filho à direita, substitui por subárvore à esquerda
        else if(atual.getFilhoDaDireita() == null){
            if(atual == raiz)
                raiz = atual.getFilhoDaEsquerda();
            else if (ehFilhoEsquerdo) // filho à esquerda do pai
                pai.setFilhoDaEsquerda(atual.getFilhoDaEsquerda());
            else                      // filho à direita do pai
                pai.setFilhoDaDireita(atual.getFilhoDaEsquerda());
        }
        // Se não há filho à esquerda, substitui por subárvore à direita
        else if(atual.getFilhoDaEsquerda() == null){
           // UTILIZAR EQUALS
            if(atual == raiz)
                raiz = atual.getFilhoDaDireita();
            else if(ehFilhoEsquerdo)
                pai.setFilhoDaEsquerda(atual.getFilhoDaDireita());
            else
                pai.setFilhoDaDireita(atual.getFilhoDaDireita());
        }
        
    }*/
    
    /**
     * Método que encontra um nó, dada uma chave.
     * Esse método percorre a árvore e retorna (se existir) um nó que contém essa chave.
     * 
     * @param chave - informação a ser buscada na árvore.
     * @return null ou um Node - se a árvore não conter a informação buscada, retorna null. Se conter, retorna o nó que a contém.
     */
    public Node encontrar(String chave) {
        Node atual = raiz;
        
        while (!atual.getDado().equals(chave)) { // Se tiver dúvidas, fazer teste de mesa utilizando a árvore da página 339
            if (chave.compareTo(atual.getDado()) < 0){
                atual = atual.getFilhoDaEsquerda();
            } 
            else{
                atual = atual.getFilhoDaDireita();
            }
            
            if (atual == null){ // Se for nulo, quer dizer que percorreu a árvore um nó que não tem mais filho,                 
                return null;  // ai recebeu nulo. Então, retorna nulo. De que não encontrou.
            }
        }
        return atual;
    }
    // vários outros métodos

    //PERCORRER:
    public void emOrdem(Node localRaiz){
        if(localRaiz != null){
            emOrdem(localRaiz.getFilhoDaEsquerda());
            System.out.println(localRaiz.getDado()+" ");
            emOrdem(localRaiz.getFilhoDaDireita());
        }
    }
    // <<<<<<<<<<<< Ver se isso é realmente necessário >>>>>>>>>>>>
    public Node minimo(){
        Node atual, ultimo = null;
        
        atual = raiz;
        while(atual != null){
            ultimo = atual;
            atual = atual.getFilhoDaEsquerda();
        }
        return ultimo;
    }
    
    
    public Node getRaiz() {return raiz;}
} // fim da classe Tree
