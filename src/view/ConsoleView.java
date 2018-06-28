package view;

import controller.Controlador;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Anésio
 */
public class ConsoleView {

    private static Controlador control = new Controlador();

    public static void main(String[] args) {
        boolean opcao = true;
        Scanner input = new Scanner(System.in);

        do {
            System.out.printf("Digite a palavra: ");
            String palavra = input.nextLine();
            ArrayList ret = control.pesquisar(palavra);
            if (ret == null) {
                System.out.println("Não foi achado!");
            } else {
                for (Object d : ret) {
                    System.out.println(d);
                }
            }
            System.out.println("(1) - Ordenar Crescente");
            int i = input.nextInt();
            
            System.out.println("Deseja sair?");
            int p = input.nextInt();
            
            if(p == 1)
                opcao = false;

            
        }while(opcao);
    }

}
