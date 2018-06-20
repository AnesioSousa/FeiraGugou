package view;

//import controller.Controlador;
import controller.Controlador;
import java.util.Iterator;
import util.ArvoreAVL;
import java.util.Scanner;
import model.Pagina;

/**
 *
 * @author Anésio
 */
class Testes {

    public static void main(String[] args) {
        ArvoreAVL tree = new ArvoreAVL();
        Controlador ctr = new Controlador();
        Scanner input = new Scanner(System.in);
        String op = null;
        //Iterator a = ctr.tree.getRaiz().listarPaginas();
        //System.out.println();
        
        
        
        /*ctr.tree.inserir("anesio");
        ctr.tree.inserir("carlos");
        ctr.tree.inserir("beatriz");
        
        Iterator<String> itr = ctr.tree.iterator();
        
        while(itr.hasNext()){
            String a = itr.next();
            System.out.println(a);
        }*/
        
        
        while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            // ERRRROOOOOO >>>>>>>> PESQUISE "rato" "rei" "rato". RATO DEVE SER A RAIZ, MAS REI ESTA SENDO, POR QUE? -> 
            // POR QUE A ÁRVORE BALANCEIA SEMPRE, MESMO ESTANDO COM 2. SE VOCE INSERIR A, E DEPOIS B, A NÃO SERÁ A RAIZ, E SIM B.
            Iterator<Pagina> itr = ctr.pesquisar(palavra);
            System.out.println(ctr.tree.getRaiz());
            System.out.println(ctr.tree.getRaiz().getVezesBuscada());
            System.out.println("Deseja sair?");
            op = input.nextLine();
        }
        Iterator<String> itr = ctr.tree.iterator();
        while(itr.hasNext()){
            String a = itr.next();
            System.out.println(a);
        }


    }
}
