package view;

import controller.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Dados;
import model.Pagina;

/**
 *
 * @author Anésio Sousa
 */
public class ConsoleView {

    private static GerenciadorDePesquisa controlSearch = new GerenciadorDePesquisa();
    private static GerenciadorDePaginas controlPages = new GerenciadorDePaginas();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList resultados;
        int opcao = 0;
        int choice = 0;
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
                                System.out.println("Insira qualquer letra ou número para continuar!");
                                input.next();
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
                    exibirMenuTopK(); // Escolha entre Top-K Palavras ou Páginas
                    choice = lerInt(true, 0, 2);
                    switch (choice) {
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
                            do{
                                exibirMenuTopKPaginas();
                                pos = lerInt(true, 0, 2);
                                switch(pos){
                                    case 1: 
                                        List<Dados> a;
                                        if(!controlPages.paginasTemAcessos()){
                                            System.out.println("Nenhuma página foi acessada !!");
                                            System.out.println("Insira qualquer letra ou número para continuar!");
                                            input.next();
                                        }else{
                                            System.out.println("Até quantas páginas deseja visualizar?");
                                            pos = lerInt(true, 1, controlPages.getPaginas().size()); // Não deixa escolher uma quantidade de palavras além da quantidade de elementos na árvore
                                            a = controlPages.topKMaisPagina(pos);
                                            for (int i = 0; i < pos; i++) {
                                                System.out.println(a.get(i));
                                            }
                                        }
                                    break;
                                    case 2: 
                                        if(!controlPages.paginasTemAcessos()){
                                            System.out.println("Nenhuma página foi acessada !!");
                                            System.out.println("Insira qualquer letra ou número para continuar!");
                                            input.next();
                                        }else{
                                            System.out.println("Até quantas páginas deseja visualizar?");
                                            pos = lerInt(true, 1, controlPages.getPaginas().size()); // Não deixa escolher uma quantidade de palavras além da quantidade de elementos na árvore
                                            a = controlPages.topKMenosPagina(pos);
                                            for (int i = 0; i < pos; i++) {
                                                System.out.println(a.get(i));
                                            }
                                        }
                                    break;
                                }
                            }while(pos != 0);
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

    private static void exibirMenuTopKPaginas(){
        System.out.println("+============================================================================+");
        System.out.println("|                              TOP-K PAGINAS                                 |");
        System.out.println("+============================================================================+");
        System.out.println("| Mais acessadas........................................................(01) |");
        System.out.println("| Menos acessadas.......................................................(02) |");
        System.out.println("+============================================================================+");
        System.out.println("| Voltar................................................................(00) |");
        System.out.println("+============================================================================+");
        System.out.print("> ");
    }

    // Pra quando o usuário pedir pra abrir algum
    private static void exibirArquivo(int i, ArrayList<Dados> results) {
        // RECEBE UMA LISTA DE "Dados" MAS O QUE EU TENHO QUE INCREMENTAR É "Pagina". :/
        // VER SE DÁ PRA REDUZIR ISSO DEPOIS
        Dados dadosRef = results.get(i);
        Pagina pag = new Pagina();
        pag.setTitulo(dadosRef.getTitulo());
        
        ArrayList<Pagina> a = controlPages.getPaginas();
        int aux = a.indexOf(pag);
        
        pag = a.get(aux);
        
        pag.incrementVezesAcessada();
        
        File arquivo = controlPages.getArquivo(pag.getTitulo());
        try {
            Scanner leitor = new Scanner(arquivo);
            System.out.println(dadosRef.getTitulo() + ": \n");
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            System.out.println();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
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
