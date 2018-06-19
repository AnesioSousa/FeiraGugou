package view;

//import controller.Controlador;
import controller.Controlador;
import java.util.Iterator;
import java.util.Scanner;
import model.Pagina;

/**
 *
 * @author Anésio
 */
class Testes {        
    public static void main(String[] args) {
        Controlador man = new Controlador();
        Scanner input = new Scanner(System.in);
        
        while(true){
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();

            Iterator itr = man.pesquisar(palavra);
            
            while(itr.hasNext()){
                Pagina p = (Pagina) itr.next();
            }
            
            if(palavra.equals("exit")){
                return;
            }
        }
        

    }
}