package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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

    private Arquivos files;
    private MergeSort mergeSort;
    private ArrayList<Dados> infoPalavras;
    private ArvoreAVL tree;
    private ArrayList<Pagina> paginas;
    private ArrayList<Pagina> adicionadas;
    private ArrayList<Pagina> editadas;
    private ArrayList<Pagina> removidas;

    public GerenciadorDePesquisa() {
        this.files = new Arquivos();
        this.mergeSort = new MergeSort();
        this.infoPalavras = new ArrayList<>();
        this.tree = new ArvoreAVL();
        this.paginas = new ArrayList<>();
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); // Necessário pra saber os arquivos add no repo.
        this.adicionadas = new ArrayList<>();
        this.editadas = new ArrayList<>();
        this.removidas = new ArrayList<>();
    }

    public ArrayList pesquisar(String palavra/*, boolean invertido*/) { // REVER ISSO DEPOIS!!!
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.

        prePesquisa();

        Palavra word = new Palavra(palavra);
        Palavra verificador = tree.encontrar(word);        // Ele já entra aqui com o repositório atualizado.

        if (verificador == null) {                     // Ver depois se não dá pra já usar "word" invés desse "verificador"
            verificador = atualizarPalavra(word, paginas);    // Isso só ocorre quando é uma palavra nova.
            if (verificador.getListaDados().isEmpty()) {      // VAI PRECISAR REMOVER O NO?
                return word.getListaDados();
            }
            tree.inserir(word);
        }
        verificador.incrementVezesBuscada();

        //atualizarTopKPalavras(word);

        /*if(invertido){
            inverter(ret.getListaDados());
        }else{
            mergeSort.sort(ret.getListaDados());
        }*/
        mergeSort.sort(verificador.getListaDados());
        return verificador.getListaDados();
    }

    private void prePesquisa() {
        if (identificarAlteracoes()) {
            atualizarPaginas();
            atualizarArvore(adicionadas, editadas, removidas);
            limparMarcadores();
        }
    }
    // ESSE MÉTODO RECEBE UMA PALAVRA E UMA LISTA DE PAGINAS, DAI ELE ATUALIZA A LISTA DE PÁGINAS DA PALAVRA COM USANDO A LISTA RECEBIDA.

    private Palavra atualizarPalavra(Palavra word, ArrayList<Pagina> pages) {
        for (Pagina pagina : pages) {

            int cont = 0;
            File arquivo = getPagina(pagina.getTitulo());   
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

    // Remover da lista principal de páginas os arquivos removidos, e marca os modificados e novos.
    private void atualizarPaginas() { // TEM QUE PASSAR AQUI ANTES DE PASSAR NA "atualizarArvore"        
        for (Pagina pag : adicionadas) {  // Adiciona à lista de páginas principal novas páginas encontradas no repositório.
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            if (!paginas.contains(p)) {   // é aqui que dá pra identificar as páginas modificadas das novas
                paginas.add(p);
            }
        }
        
        for (Pagina pag : removidas) {
            Pagina p = new Pagina();
            p.setTitulo(pag.getTitulo());
            paginas.remove(p);
        }
    }

    private void limparMarcadores() {
        editadas.clear();
        adicionadas.clear();
        removidas.clear();
    }

    public boolean arqIsModified() {  // TESTAR ISSO!
        
        //limparMarcadores();
        boolean a = identificarAlteracoes();
        
        // a == true se algo foi modificado
        
        limparMarcadores();
        
        return a;
    }

    private boolean identificarAlteracoes() {  // RETORNA true SE ALGO FOI MODIFICADO
        boolean flag = false;

        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getPagina(paginas.get(i).getTitulo());
            if (arq == null) {
                flag = true;
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i));
            }
        }
        
        mergeSort.sort(paginas);

        for (int i = 0; i < paginas.size(); i++) {  // Pega os arquivos editados
            File arq = getPagina(paginas.get(i).getTitulo());
            //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
            if (paginas.get(i).getInfo() != arq.lastModified()) {
                flag = true;
                editadas.add(paginas.get(i));
            }
        }
        
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE MODIFICADOS.*/
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());

        
        mergeSort.sort(aComparar);

        if (paginas.size() < aComparar.size()) {
            for (int i = 0; i < aComparar.size(); i++) {
                Pagina p = new Pagina();
                p.setTitulo(aComparar.get(i).getTitulo());
                if (!paginas.contains(p)) {
                    flag = true;
                    p.setTitulo(aComparar.get(i).getTitulo());
                    p.setInfo(aComparar.get(i).getInfo());
                    adicionadas.add(p);
                }
            }
        }

        return flag;
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

    public File getPagina(String titulo) {
        ArrayList<File> arq = files.obterRepositorio();
        for (File arquivo : arq) {
            if (arquivo.getName().equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    public ArrayList<Dados> inverterResultados(ArrayList<Dados> lista) {
            for(int i = 0, j = lista.size() - 1; i < j; i++) {
                lista.add(i, lista.remove(j));
            }
            return lista;
        }

    // REVER E TESTAR ISSO.
    public ArrayList topKMaisPalavra(int qtd) {  // Fazer pra palavra e pra página // FAZER EXCEÇÃO DE QUE SE O USUÁRIO QUISER UM TOP (TAMANHO) MAIOR DO QUE O NÚMERO DE ELEMENTOS.

        return (ArrayList) infoPalavras.subList(0, qtd);
    }

    // REVER E TESTAR ISSO.
    public ArrayList topKMenosPalavra(int qtd) {// Fazer pra palavra e pra página

        return (ArrayList) infoPalavras.subList(infoPalavras.size(), qtd);
    }

    public ArrayList listarPaginas() {
        return paginas;
    }

    // REVER E TESTAR ISSO.
    private void atualizarTopKPalavras(Palavra word) { // PAREI DE UTILIZAR O NODEEE.. TEM QUE REVER ISSOO!!! // TEM QUE BOTAR CONDIÇÃO DE QUE SE A PALAVRA JÁ ESTIVER NA LISTA SÓ MUDAR A QUANTIDADE.
        Dados p = new Dados();
        p.setTitulo(word.getChave());
        p.setQuantidade(word.getVezesBuscada());
        infoPalavras.add(p);
        mergeSort.sort(infoPalavras);
    }

}
