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
  
        while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            
            System.out.println(ctr.pesquisar(palavra));
            /*Iterator<Pagina> itr = ctr.pesquisar(palavra);
            if(!itr.hasNext()){
                System.out.println("Não foi achado!");
            }else{
                while(itr.hasNext()){
                Pagina a = itr.next();
                System.out.println(a);
                }
            }*/
            
            
            System.out.println("Deseja sair?");
            op = input.nextLine();
        }
        

    }
}
