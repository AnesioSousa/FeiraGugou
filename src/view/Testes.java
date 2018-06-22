package view;

//import controller.Controlador;
import controller.Controlador;
import java.io.File;
import java.util.Iterator;
import util.ArvoreAVL;
import java.util.Scanner;
import model.Pagina;
import util.Arquivos;

/**
 *
 * @author Anésio
 */
class Testes {
    public static void main(String[] args) {
        ArvoreAVL tree = new ArvoreAVL();
        Controlador ctr = new Controlador();
        Arquivos man = new Arquivos();
        Scanner input = new Scanner(System.in);
        String op = null;
  
        while( !"sair".equals(op)){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();

            Iterator<Pagina> itr = ctr.pesquisar(palavra);
            if(!itr.hasNext()){
                System.out.println("Não foi achado!");
            }else{
                while(itr.hasNext()){
                Pagina a = itr.next();
                System.out.println(a);
                }
            }
            
            
            System.out.println("Deseja sair?");
            op = input.nextLine();
        }
        
        /*Iterator<Long> itr = ctr.getAlteracoes();
        
        while(itr.hasNext()){
            long a = itr.next();
            
            System.out.println(a);
        }*/
        
    }
}
