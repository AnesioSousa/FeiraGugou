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
 * Classe responsável por gerar objetos capazes de gerenciar as pesquisas do software.
 * @author Anésio Sousa
 */
public class GerenciadorDePesquisa {

    private MergeSort mergeSort;
    private ArrayList<Dados> infoPalavras;
    private GerenciadorDePaginas controlPages;
    /**
     * Construtor da classe GerenciadorDePesquisa, nele são inicializados os atributos da classe.
     */
    public GerenciadorDePesquisa() {
        this.mergeSort = new MergeSort();
        this.infoPalavras = new ArrayList<>();
        this.controlPages = new GerenciadorDePaginas();
    }
    /**
     * Recebe uma palavra a ser pesquisada e retorna uma lista de paginas na qual tal palavra incide.
     * @param palavra palavra a ser pesquisada.
     * @return lista contendo resultados
     */
    public ArrayList pesquisar(String palavra) {
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.

        prePesquisa();
        Palavra word = new Palavra(palavra);
        Palavra verificador = controlPages.getControlTree().getTree().encontrar(word);        // Ele já entra aqui com o repositório atualizado.

        if (verificador == null) {
            verificador = atualizarPalavra(word, controlPages.getPaginas());    //REVER ISSSOOO!!!!!!// Isso só ocorre quando é uma palavra nova.
            if (verificador.getListaDados().isEmpty()) {      
                return word.getListaDados();
            }
            controlPages.getControlTree().getTree().inserir(word);
        }
        verificador.incrementVezesBuscada();
        atualizarTopKPalavras(verificador);
        mergeSort.sort(verificador.getListaDados());
        
        return verificador.getListaDados();
    }
    
    /**
     * Método auxiliar que verifica a integridade dos arquivos antes de fazer uma busca.
     */
    private void prePesquisa() { 
        if (controlPages.identificarAlteracoes()) {
            controlPages.atualizarPaginas();
            atualizarArvore(controlPages.getAdicionadas(), controlPages.getEditadas(), controlPages.getRemovidas()); 
            controlPages.limparMarcadores();
        }
    }
    
    private void atualizarArvore(ArrayList<Pagina> adicionadas, ArrayList<Pagina> alteradas, ArrayList<Pagina> removidas) {
        // DE UMA SÓ VEZ:
        // percorrer a lista de modificados verificando se a Palavra incide em alguma página modificada ou nova e à atualizar.
        // percorrer a lista de removidos verificando se a Palavra tem alguma página que foi removida e tirar. (provavelemte seja a ultima coisa a fazer)

        ArrayList<Palavra> palavrasParaRemover = new ArrayList<>();
        Iterator<Palavra> itr = controlPages.getControlTree().getTree().iterator();

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
            controlPages.getControlTree().getTree().remover(p);
        }
    }
    
    /**
     * Recebe um objeto Palavra e uma lista de Páginas.
     * Esse método percorre toda a lista de páginas recebidas, procurando ocorrencias
     * dessa palavra. Se achar, insere uma referencia de nomes das páginas e quantas
     * vezes tal palavra ocorre nessas páginas numa lista de Dados que ficam armazenados
     * na Palavra.
     * @param word Palavra a ser verificada
     * @param pages lista de palavras que vai ser percorrida.
     * @return palavra com os dados verificados.
     */
    private Palavra atualizarPalavra(Palavra word, ArrayList<Pagina> pages) {
        for (Pagina pagina : pages) {

            int cont = 0;
            File arquivo = controlPages.getArquivo(pagina.getTitulo());   // REVER O USO DO CONTROLADOR AQUI. 
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
                    }
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
                } else {                                              // Se o contador for 0 e essa Palavra tiver essa Página em seus dados,
                    if (pos != -1) {                                  // quer dizer que antes tinha ocorrencias, mas deixou de ter. Então essa Página é removida da lista de dados dessa Palavra.
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
    /**
     * "Limpa" uma linha recebida, removendo tudo que não seja letra ou número.
     * @param str2Clean linha a ser "limpada".
     * @return string que armazena a string pós limpeza.
     */
    private String prepararLinha(String str2Clean) {
        Pattern padrao = Pattern.compile("[\\p{L}0-9]+{1,}"); 
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

    /**
     * Dada uma lista, inverte seus elementos.
     * @param lista lista a ser invertida.
     * @return lista invertida.
     */
    public ArrayList<Dados> inverterResultados(ArrayList<Dados> lista) {
        for (int i = 0, j = lista.size() - 1; i < j; i++) {
            lista.add(i, lista.remove(j));
        }
        return lista;
    }

    /**
     * Retorna as N palavras mais procuradas.
     * @param qtd quantidade de palavras a ser exibida.
     * @return sublista da lista de palavras auxiliar contendo as N palavras.
     */
    public List topKMaisPalavra(int qtd) {
        mergeSort.sort(infoPalavras);
        List<Dados>  aux = infoPalavras.subList(0, qtd);
        return aux;
    }

    /**
     * Retorna as N palavras menos procuradas.
     * @param qtd quantidade de palavras a ser exibida.
     * @return sublista da lista de palavras auxiliar contendo as N palavras.
     */
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
    /**
     * Método que atualiza a lista auxiliar que armazena os elementos pesquisados para fins de ranking.
     * @param word palavra a ser adicionada ou atualizada na lista.
     */
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

    /**
     * Retorna o controlador de páginas.
     * @return controlador de páginas.
     */
    public GerenciadorDePaginas getControlPages() {
        return controlPages;
    }
    
    /**
     * Retorna uma lista auxiliar que armazena os elementos pesquisados para fins de ranking.
     * @return lista de elementos.
     */
    public ArrayList<Dados> getInfoPalavras() {
        return infoPalavras;
    }
}
