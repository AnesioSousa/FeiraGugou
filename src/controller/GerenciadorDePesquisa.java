package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;
import model.*;
import util.*;

/**
 *
 * @author Anésio Sousa
 */
public class GerenciadorDePesquisa {

    private MergeSort mergeSort;
    private ArrayList<Dados> infoPalavras;
    private ArvoreAVL tree;
    private GerenciadorDePaginas ctrl;

    public GerenciadorDePesquisa() {
        this.mergeSort = new MergeSort();
        this.infoPalavras = new ArrayList<>();
        this.tree = new ArvoreAVL();
        this.ctrl = new GerenciadorDePaginas();
    }

    public ArrayList pesquisar(String palavra/*, boolean invertido*/) { // REVER ISSO DEPOIS!!!
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.

        //prePesquisa();
        Palavra word = new Palavra(palavra);
        Palavra verificador = tree.encontrar(word);        // Ele já entra aqui com o repositório atualizado.

        if (verificador == null) {                     // Ver depois se não dá pra já usar "word" invés desse "verificador"
            verificador = atualizarPalavra(word, ctrl.getPaginas());    //REVER ISSSOOO!!!!!!// Isso só ocorre quando é uma palavra nova.
            if (verificador.getListaDados().isEmpty()) {      // VAI PRECISAR REMOVER O NO?
                return word.getListaDados();
            }
            tree.inserir(word);
        }
        verificador.incrementVezesBuscada();

        atualizarTopKPalavras(verificador);

        /*if(invertido){
            inverter(ret.getListaDados());
        }else{
            mergeSort.sort(ret.getListaDados());
        }*/
        mergeSort.sort(verificador.getListaDados());
        return verificador.getListaDados();
    }

    private void atualizarArvore(ArrayList<Pagina> adicionadas, ArrayList<Pagina> alteradas, ArrayList<Pagina> removidas) {
        // DE UMA SÓ VEZ:
        // percorrer a lista de modificados verificando se a Palavra incide em alguma página modificada ou nova e à atualizar.
        // percorrer a lista de removidos verificando se a Palavra tem alguma página que foi removida e tirar. (provavelemte seja a ultima coisa a fazer)

        ArrayList<Palavra> palavrasParaRemover = new ArrayList<>();
        Iterator<Palavra> itr = tree.iterator();

        while (itr.hasNext()) { //Percorre as Palavras da árvore tirando da lista de páginas delas as páginas removidas do repositório. 
            Palavra n = itr.next();
            ArrayList<Dados> dadosPalavra = n.getListaDados();

            atualizarPalavra(n, alteradas); // Atualiza os dados da Palavra verificando os arquivos alterados e adicionados.
            atualizarPalavra(n, adicionadas);

            for (int i = 0; i < removidas.size(); i++) { // Remove da Palavra (se tiver) as Páginas removidas.
                Dados data = new Dados();
                data.setTitulo(removidas.get(i).getTitulo());
                int aux = dadosPalavra.indexOf(data);
                if (aux != -1) {                          // SE FOR ENCONTRADO O TITULO
                    dadosPalavra.remove(aux);
                }
            }

            if (n.getListaDados().isEmpty()) {     // SE APÓS A REMOÇÃO, SE A PALAVRA NÃO TEM MAIS NINGUEM NA LISTA DE PÁGINA, ESSA PALAVRA É MARCADA PARA REMOÇÃO DA ÁRVORE
                palavrasParaRemover.add(n);              // Marca a palavra se ela não tiver incidencia em nenhuma página.
            }
        }
        for (Palavra p : palavrasParaRemover) { // E ENTÃO REMOVIDA!
            tree.remover(p);
        }
    }
    // ESSE MÉTODO RECEBE UMA PALAVRA E UMA LISTA DE PAGINAS, DAI ELE ATUALIZA A LISTA DE PÁGINAS DA PALAVRA COM USANDO A LISTA RECEBIDA.

    private Palavra atualizarPalavra(Palavra word, ArrayList<Pagina> pages) {
        for (Pagina pagina : pages) {

            int cont = 0;
            File arquivo = ctrl.getArquivo(pagina.getTitulo());   // REVER O USO DO CONTROLADOR AQUI. 
            try {
                Scanner input = new Scanner(arquivo);
                while (input.hasNext()) {
                    String linha = input.nextLine();

                    String aux = prepararLinha(linha);

                    StringTokenizer tkn = new StringTokenizer(aux);

                    while (tkn.hasMoreTokens()) {
                        String cmp = tkn.nextToken();

                        if (cmp.equalsIgnoreCase(word.getChave())) {
                            cont++;
                        }

                        //System.out.print(cmp+" ");
                    }
                    //System.out.println();
                }
                Dados data = new Dados();
                data.setTitulo(pagina.getTitulo());

                int pos = word.getListaDados().indexOf(data);
                if (cont != 0) { // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                    if (pos != -1) {
                        Dados aux = word.getListaDados().get(pos);
                        aux.setQuantidade(cont);
                    } else {
                        ArrayList<Dados> a = word.getListaDados();
                        data.setTitulo(arquivo.getName());
                        data.setQuantidade(cont);
                        a.add(data);
                    }
                } else {                                              // SE O CONTADOR FOR 0 E ESSE NÓ TIVER ESSA ESSA PAGINA EM SEUS DADOS, QUER DIZER QUE ANTES TINHA OCORRENCIAS, MAS DEIXOU DE TER. DEVE REMVER ENTÃO ESSA PÁGINA DA LISTA DE DADOS DESSE NÓ.
                    if (pos != -1) {
                        ArrayList<Dados> a = word.getListaDados();
                        a.remove(pos);                               // Remove a Página da lista de Páginas de uma Palavra
                    }
                }
                input.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
        return word;
    }
    
    private boolean verificarMultiPalavras(String palavra) {
        Pattern padrao = Pattern.compile("\\s+[A-Za-z]+");
        Matcher verificador = padrao.matcher(palavra);

        return verificador.find();
    }

    private String prepararLinha(String str2Clean) {
        Pattern padrao = Pattern.compile("[\\p{L}0-9]+{1,}"); // ver se dá pra retirar o {1,}
        Matcher verificador = padrao.matcher(str2Clean);
        String palavrasPreparadas;
        String a = "";

        while (verificador.find()) {
            if (verificador.group().length() != 0) {
                palavrasPreparadas = verificador.group();
                a += palavrasPreparadas + " ";
            }
        }

        return a;
    }

    public ArrayList<Dados> inverterResultados(ArrayList<Dados> lista) {
        for (int i = 0, j = lista.size() - 1; i < j; i++) {
            lista.add(i, lista.remove(j));
        }
        return lista;
    }

    public List topKMaisPalavra(int qtd) {
        mergeSort.sort(infoPalavras);
        List<Dados>  aux = infoPalavras.subList(0, qtd);
        return aux;
    }

    public List topKMenosPalavra(int qtd) {
        Comparator<Dados> comp = new Comparator<Dados>() {
            @Override
            public int compare(Dados o1, Dados o2) {
                if (o1.getQuantidade() > o2.getQuantidade()) {
                    return 1;
                }
                if (o1.getQuantidade() < o2.getQuantidade()) {
                    return -1;
                }
                
                return 0;
            }
        };
        mergeSort.sort(infoPalavras, comp);
        List<Dados> aux = infoPalavras.subList(0, qtd);        
        return aux;
    }
    
    private void atualizarTopKPalavras(Palavra word) {
        Dados p = new Dados();
        p.setTitulo(word.getChave());
        
        int aux = infoPalavras.indexOf(p);
        
        if(aux != -1){
           infoPalavras.get(aux).setQuantidade(word.getVezesBuscada());
        }else{
           p.setQuantidade(word.getVezesBuscada());
           infoPalavras.add(p);
        }
    }

    public ArrayList<Dados> getInfoPalavras() {
        return infoPalavras;
    }
}
