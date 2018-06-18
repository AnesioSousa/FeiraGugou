package util;

public class TreeApp {
    public static void main(String[] args) {
        ArvoreAVL tree = new ArvoreAVL();  
        
        String[] palavras = new String[]{"anesio","beatriz", "carlos"};
        
        tree.inserir(palavras[0]);
        System.out.println(tree.getRaiz());
        tree.inserir(palavras[1]);
        System.out.println(tree.getRaiz());
        System.out.println(tree.getRaiz().getEsquerda());
        tree.inserir(palavras[2]);
        System.out.println(tree.getRaiz().getDireita());

        System.out.println(tree.contains("anesio"));
    } 
} 
