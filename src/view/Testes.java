package view;

import controller.Controlador;
import java.util.Scanner;
/**
 *
 * @author Anésio
 */
public class Testes {
    private static Controlador man = new Controlador();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
                
        System.out.printf("Digite a palavra:");
        System.out.println();
        String palavra = input.nextLine();
        
        man.pesquisar(palavra);
    }
}
