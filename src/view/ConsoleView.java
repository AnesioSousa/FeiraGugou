package view;

import controller.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Dados;

/**
 *
 * @author Anésio
 */
public class ConsoleView {

    private static GerenciadorDePesquisa controlSearch = new GerenciadorDePesquisa();
    private static GerenciadorDePaginas controlPages = new GerenciadorDePaginas();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList resultados;
        boolean repetirMenuPrincipal = false;
        int opcao = 0;
        int choice = 0;
        int escolha = 0;
        int pos = 0;

        do {
            exibirMenuPrincipal();
            opcao = lerInt(true, 0, 2);
            switch (opcao) {
                case 1:
                    resultados = pesquisa();
                    do {
                        exibirResultados(resultados);                                                               // <<<<<<<<<<<<<<                
                        exibirMenuResultados();
                        choice = lerInt(true, 0, 3);
                        switch (choice) {
                            case 1:
                                System.out.println("Digite o número da página:");
                                pos = lerInt(true, 0, resultados.size());
                                System.out.println();
                                exibirArquivo(pos - 1, resultados);
                            break;
                            case 2:
                                controlSearch.inverterResultados(resultados);
                            break;
                            case 3:
                                resultados = pesquisa();
                            break;
                        }
                    } while (choice != 0);
                break;
                case 2:
                    exibirMenuTopK();
                    choice = lerInt(true, 0, 2);
                    switch (choice) {             // Escolha entre Top-K Palavras ou Páginas
                        case 1: // SE ESCOLHER PALAVRA:
                            do{
                                exibirMenuTopKPalavras();
                                pos = lerInt(true, 0, 2);
                                switch(pos){ // ESCOLHA DE MAIOR PALAVRA E MENOR PALAVRA!
                                    case 1:
                                        List<Dados> a;
                                        if(controlSearch.getInfoPalavras().isEmpty()){
                                            System.out.println("Nenhuma palavra ainda foi buscada!");
                                            System.out.println("Insira qualquer letra ou número para continuar!");
                                            input.next();
                                        }else{
                                            System.out.println("Até quantas palavras deseja visualizar?");
                                            pos = lerInt(true, 1, controlSearch.getInfoPalavras().size()); // Não deixa escolher uma quantidade de palavras além da quantidade de elementos na árvore
                                            a = controlSearch.topKMaisPalavra(pos);
                                            for (int i = 0; i < pos; i++) {
                                                System.out.println(a.get(i));
                                            }
                                        }
                                    break;
                                    case 2:
                                        if(controlSearch.getInfoPalavras().isEmpty()){
                                            System.out.println("Nenhuma palavra ainda foi buscada!");
                                            System.out.println("Insira qualquer letra ou número para continuar!");
                                            input.next();
                                        }else{
                                            System.out.println("Até quantas palavras deseja visualizar?");
                                            pos = lerInt(true, 1, controlSearch.getInfoPalavras().size()); // Não deixa escolher uma quantidade de palavras além da quantidade de elementos na árvore
                                            a = controlSearch.topKMenosPalavra(pos);
                                            for (int i = 0; i < pos; i++) {
                                                System.out.println(a.get(i));
                                            }
                                        }
                                    break;
                                }
                            }while(pos != 0);    
                        break;
                        case 2: // SE ESCOLHER PAGINA:
                            
                        break;
                    }
                break;

            }
        } while (opcao != 0);
    }
  
    private static void exibirMenuPrincipal() {
        System.out.println("+============================================================================+");
        System.out.println("|                               FeiraGugou                                   |");
        System.out.println("+============================================================================+");
        System.out.println("| Realizar Busca........................................................(01) |");
        System.out.println("| Top-K.................................................................(02) |");
        System.out.println("+============================================================================+");
        System.out.println("| Sair..................................................................(00) |");
        System.out.println("+============================================================================+");
        System.out.print("> ");
    }
    
    private static ArrayList pesquisa(){
        String palavra = obterPalavra();
        ArrayList aux = controlSearch.pesquisar(palavra);
        
        return aux;
    }
    
    private static void exibirMenuTopK(){ 
        System.out.println("+============================================================================+");
        System.out.println("|                                 TOP-K                                      |");
        System.out.println("+============================================================================+");
        System.out.println("| Palavras..............................................................(01) |");
        System.out.println("| Paginas...............................................................(02) |");
        System.out.println("+============================================================================+");
        System.out.println("| Sair..................................................................(00) |");
        System.out.println("+============================================================================+");
        System.out.print("> ");
    }
    
    private static void exibirMenuResultados(){
        System.out.println("+============================================================================+");
        System.out.println("| Abrir página..........................................................(01) |");
        System.out.println("| Alterar ordem dos resultados..........................................(02) |");
        System.out.println("| Realizar outra busca..................................................(03) |");
        System.out.println("+============================================================================+");
        System.out.println("| Voltar ao menu principal..............................................(00) |");
        System.out.println("+============================================================================+");
        System.out.print("> ");
    }
    
    private static String obterPalavra() {
        System.out.printf("Digite a palavra: ");
        String palavra = input.nextLine();

        return palavra;
    }

    private static void exibirResultados(ArrayList results) {
        if (results.isEmpty()) {
            System.out.println("Não foi achado!"); // SE NÃO FOI ACHADO, NO SUBMENU1 SÓ MOSTRAR "Realizar outra busca"
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(i + 1 + " - " + results.get(i));
            }
        }
    }
    
    private static void exibirMenuTopKPalavras(){
        int opcao = 0;
        System.out.println("+============================================================================+");
        System.out.println("|                             TOP-K PALAVRAS                                 |");
        System.out.println("+============================================================================+");
        System.out.println("| Mais buscadas.........................................................(01) |");
        System.out.println("| Menos buscadas........................................................(02) |");
        System.out.println("+============================================================================+");
        System.out.println("| Voltar................................................................(00) |");
        System.out.println("+============================================================================+");
        System.out.print("> ");
    }

    private static int topKPaginas(int tamanho) throws IOException {
        int opcao = 0;
        barra(tamanho, true);
        textoSimples(tamanho, "Top-k", true, true);
        separador(tamanho, true);
        novoItem(tamanho, "Mais visitadas", "1", true);
        novoItem(tamanho, "Menos visitadas", "2", true);
        separador(tamanho, true);
        novoItem(tamanho, "Sair", "0", true);
        barra(tamanho, true);

        opcao = Console.readInt();

        return opcao;
    }

    private static int obterNumero() throws IOException {   ////////////////////// TRATAAARRR EXCEÇÃO
        int opcao = 0;
        System.out.print("Digite um número valido: ");
        opcao = Console.readInt();

        return opcao;
    }

    // Pra quando o usuário pedir pra abrir algum
    private static void exibirArquivo(int i, ArrayList results) {
        Dados p = (Dados) results.get(i);
        File arquivo = controlPages.getPagina(p.getTitulo());
        try {
            Scanner leitor = new Scanner(arquivo);
            System.out.println(p.getTitulo() + ": \n");
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            System.out.println();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
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
                        System.out.println("Digite um valor entre: " + min + " e " + max + ": ");
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
