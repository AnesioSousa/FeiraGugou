package view;

//import controller.Controlador;
import controller.Controlador;
import java.util.Iterator;
import java.util.Scanner;
import model.Pagina;
import util.ArvoreAVL;

/**
 *
 * @author Anésio
 */
class Testes {

    public static void main(String[] args) {
        ArvoreAVL tree = new ArvoreAVL();
        /*Controlador ctr = new Controlador();
        Scanner input = new Scanner(System.in);
        String op = null;*/
        //Iterator a = ctr.tree.getRaiz().listarPaginas();
        //System.out.println();
        
        
        
        tree.inserir("anesio");
        tree.inserir("beatriz");
        tree.inserir("carlos");
        Iterator<String> itr = tree.iterator();
        
        while(itr.hasNext()){
            String a = itr.next();
            System.out.println(a);
        }
        
        
        
        /*while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            // ERRRROOOOOO >>>>>>>> PESQUISE "rato" "rei" "rato". RATO DEVE SER A RAIZ, MAS REI ESTA SENDO, POR QUE?
            Iterator<Pagina> itr = ctr.pesquisar(palavra);
            System.out.println(ctr.tree.getRaiz());
            System.out.println(ctr.tree.getRaiz().getVezesBuscada());
            System.out.println("Deseja sair?");
            op = input.nextLine();
        }*/
        /*while(itr.hasNext()){
            Pagina a = itr.next();
            
            System.out.println(a);
        }*/


    }
}
