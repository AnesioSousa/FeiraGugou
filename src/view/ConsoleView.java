package view;

import controller.Controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Dados;

/**
 *
 * @author Anésio
 */
public class ConsoleView {

    private static Controlador control = new Controlador();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {  ////////////////////// TRATAAARRR EXCEÇÃO
        final int TAMANHO_MENU = 80;
        int opcao = 0;

        do {
            menuPrincipal(TAMANHO_MENU);
            opcao = Console.readInt();
            switch(opcao){
                case 1: {
                        int choice = 0;
                        int pos = 0;
                        String palavra = obterPalavra();
                        ArrayList ret = control.pesquisar(palavra); 
                        do {
                            exibirResultados(ret);                                                               // <<<<<<<<<<<<<<                
                            choice = subMenu1(TAMANHO_MENU);
                            switch (choice) { 
                                case 1:{
                                        if(filesAreOK() == true){     // TA COM BOOO, VERIFICAR ISSO DEPOIS -> 
                                            pos = subMenu2();
                                            exibirArquivo(pos - 1, ret);
                                        }else{
                                            System.out.println("ARQUIVOS FORAM ALTERADOS, A BASE DE DADOS E A LISTA DE RESULTADOS AGORA SERÃO ATUALIZADOS!!");
                                            //ret = control.pesquisar(palavra);
                                        }
                                    }
                            }
                            System.out.println("Tecle ENTER para continuar...");
                            Console.readChar();
                        } while(choice != 3);
                
                    }
                case 2: 
                System.out.println("Tecle ENTER para continuar...");
                Console.readChar();
            }
            
        }while(opcao != 0);
    }
    
    private static String obterPalavra(){
        System.out.printf("Digite a palavra: ");
        String palavra = input.nextLine();
        
        return palavra;
    }
    
    private static void exibirResultados(ArrayList results){
        if (results.isEmpty()) {
            System.out.println("Não foi achado!"); // SE NÃO FOI ACHADO, NO SUBMENU1 SÓ MOSTRAR "Realizar outra busca"
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(i+1 +" - " + results.get(i));
            }
        }
    }
    
    private static int subMenu1(int tamanho) throws IOException{  ////////////////////// TRATAAARRR EXCEÇÃO
        int opcao = 0;
        barra(tamanho, true);
        novoItem(tamanho, "Abrir página", "1", true);
        novoItem(tamanho, "Alterar ordem", "2", true);
        novoItem(tamanho, "Realizar outra busca", "3", true);
        barra(tamanho, true);
        opcao = Console.readInt();
        
        return opcao;
    }
    
    private static int subMenu2() throws IOException{   ////////////////////// TRATAAARRR EXCEÇÃO
        int opcao = 0;
        System.out.print("Digite o número da página: ");
        opcao = Console.readInt();
        
        return opcao;
    }

    private static boolean filesAreOK() {        
        return control.osArquivosEstaoIntegros();
    }
    
    // Pra quando o usuário pedir pra abrir algum
    private static void exibirArquivo(int i, ArrayList results){
        Dados p = (Dados) results.get(i);
        File arquivo = control.getPagina(p.getTitulo());
        try {
            Scanner leitor = new Scanner(arquivo);
            System.out.println("Conteúdo da página: "+ p.getTitulo() +"\n");
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            System.out.println();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } 
    }
    
    private static void menuPrincipal(int tamanho) {
        barra(tamanho, true);
        textoSimples(tamanho, "FeiraGugou", true, true);
        separador(tamanho, true);
        novoItem(tamanho, "Realizar Busca", "1", true);
        novoItem(tamanho, "Top-K", "2", true);
        separador(tamanho, true);
        novoItem(tamanho, "Sair", "0", true);
        barra(tamanho, true);
    }
    
    private static void barra(int tamanho, boolean pulaLinha) {
        System.out.print("+");
        for (int i = 0; i < tamanho; i++) {
            System.out.print("=");
        }
        if (pulaLinha) {
            System.out.println("+");
        } else {
            System.out.print("+");
        }
    }
    
    private static void textoSimples(int tamanho, String texto, boolean centralizar, boolean pulaLinha) {
        int tamanhoDaBarra = tamanho - texto.length();
        int espacosDaEsquerda, espacosDaDireita;
        espacosDaEsquerda = centralizar ? tamanhoDaBarra / 2 : 1;
        espacosDaDireita = tamanhoDaBarra - espacosDaEsquerda;
        fazTraco(espacosDaEsquerda, ' ', true, false);
        System.out.print(texto);
        fazTraco(espacosDaDireita, ' ', false, pulaLinha);
    }
    
    private static void separador(int tamanho, boolean comTraco) {
        char lateral = '|', meio = ' ';
        if (comTraco == true) {
            lateral = '+';
            meio = '=';
        }
        System.out.print(lateral);
        fazTraco(tamanho, meio, false, false);
        System.out.println(lateral);
    }
    
    private static void novoItem(int tamanho, String item, String pesoDoItem, boolean pulaLinha) {

        int qtdDeTracos = ((tamanho - item.length()) - (pesoDoItem.length() + 4));
        if (pesoDoItem.length() == 1) {
            qtdDeTracos--;
        }
        System.out.print("| " + item);
        fazTraco(qtdDeTracos, '.', false, false);
        pesoDoItem = (pesoDoItem.length() >= 2) ? "(" + pesoDoItem + ")" : "(0" + pesoDoItem + ")";
        System.out.print(pesoDoItem);
        if (pulaLinha) {
            System.out.println(" |");
        } else {
            System.out.print(" |");
        }

    }
    
     private static void mensagem(int tamanho, String mensagem, boolean poemEscolha) {
        barra(tamanho, true);
        textoSimples(tamanho, mensagem, true, true);
        if (poemEscolha) {
            textoDuplo(tamanho, "Sim__(01)", true, "Não__(02)", true);
        }
        barra(tamanho, true);

    }

    private static String fazTraco(int tamanho, char estilo, boolean bordaE, boolean bordaD) {
        String s = "";
        if (bordaE) {
            System.out.print("|");
        }
        for (int i = 0; i < tamanho; i++) {
            System.out.print(estilo);
            s += estilo;
        }
        if (bordaD) {
            System.out.println("|");
        }
        return s;
    }
    
    private static void textoDuplo(int tamanho, String txt1, boolean cTxt1, String txt2, boolean cTxt2) {
        textoSimples(tamanho / 2 - 1, txt1, cTxt1, false);
        textoSimples(tamanho / 2, txt2, cTxt2, true);
    }
    
    private static int lerInt(boolean limite, int min, int max) {
        Scanner input = new Scanner(System.in);
        int valor;
        boolean repetir;
        do {
            try {
                valor = input.nextInt();
                input.nextLine();
                if ((limite && max == 0) && (valor >= min)) {
                    return valor;
                } else if (limite) {
                    if (valor >= min && valor <= max) {
                        return valor;
                    } else {
                        System.out.print("Digite um valor entre: " + min + " e " + max + ": ");
                        repetir = true;
                    }
                } else {
                    return valor;
                }

            } catch (Exception e) {
                input.nextLine();
                repetir = true;
            }
        } while (repetir);
        return 0;
    }
}
