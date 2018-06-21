package view;

//import controller.Controlador;
import controller.Controlador;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.ArvoreAVL;
import java.util.Scanner;
import java.util.Vector;
import model.Pagina;
import java.util.regex.*;

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
  
        while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            
            Iterator<Pagina> itr = ctr.pesquisar(palavra);
            while(itr.hasNext()){
                Pagina a = itr.next();
                System.out.println(a);
            }
            
            System.out.println("Deseja sair?");
            op = input.nextLine();
        }

    }
}
