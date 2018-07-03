package controller;

import util.ArvoreAVL;

/**
 * Classe responsável por gerar objetos capazes de gerenciar árvores AVL.
 * @author Anésio Sousa
 */
public class GerenciadorDeArvores {
    private ArvoreAVL tree;

    /**
     * Contrutor da classe GerenciadorDeArvores, nele são inicializados os atributos da classe.
     */
    public GerenciadorDeArvores() {
        this.tree = new ArvoreAVL();
    }

    /**
     * Retorna a árvore que esse gerenciador armazena atualmente.
     * @return arvore atual.
     */
    public ArvoreAVL getTree() {
        return tree;
    }

    /**
     * Dita qual árvore esse gerenciador irá passar a ter.
     * @param tree nova árvore.
     */
    public void setTree(ArvoreAVL tree) {
        this.tree = tree;
    }

}
