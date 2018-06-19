package view;

//import controller.Controlador;
import controller.Controlador;
import java.util.Scanner;

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

            man.pesquisar(palavra);
            
            if(palavra.equals("exit")){
                return;
            }
        }
        

    }
}