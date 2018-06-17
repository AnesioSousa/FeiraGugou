package util;

public class TreeApp {
    public static void main(String[] args) {
        Tree tree = new Tree();  
        
        String[] palavras = new String[]{"anesio","beatriz", "carlos"};
        
        tree.inserir(palavras[0]);
        System.out.println(tree.getRaiz());
        tree.inserir(palavras[1]);
        System.out.println(tree.getRaiz().getEsquerda());
        tree.inserir(palavras[2]);
        System.out.println(tree.getRaiz().getDireita());
        
        
        System.out.println(tree.getRaiz());
        System.out.println(tree.getRaiz().getEsquerda());
        Node found = tree.encontrar("anesio"); 
        
        //theTree.emOrdem(theTree.getRaiz());
       /* if(found != null)
            System.out.println(found);
        else
            System.out.println("Could not find node with key 25");
        */
    } 
} 
