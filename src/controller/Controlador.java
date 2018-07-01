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
import util.ArvoreAVL.Node;

/**
 *
 * @author Anésio Sousa
 */
public class Controlador {

    private Arquivos files;
    private MergeSort mergeSort;
    private ArrayList<Dados> infoPalavras;
    private ArvoreAVL tree;
    private ArrayList<Pagina> paginas;

    public Controlador() {
        this.files = new Arquivos();
        this.mergeSort = new MergeSort();
        this.infoPalavras = new ArrayList<>();
        this.tree = new ArvoreAVL();
        this.paginas = new ArrayList<>(); 
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio());
    }

    public ArrayList pesquisar(String palavra/*, boolean invertido*/) { // REVER ISSO DEPOIS!!!
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        verificarIntegridade(paginas);
        
        Palavra word = new Palavra(palavra);
        Node ret = tree.encontrar(word);        // Ele já entra aqui com o repositório atualizado.

        if (ret == null) {
            tree.inserir(word); // tirar isso
            ret = tree.encontrar(word);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
            if (ret.getKey().getListaDados().isEmpty()) {
                tree.remover(word);
                return word.getListaDados();  // Era return null;
            }
        }
        ret.getKey().incrementVezesBuscada();
        
        atualizarTopKPalavras(ret);

        /*if(invertido){
            inverter(ret.getListaDados());
        }else{
            mergeSort.sort(ret.getListaDados());
        }*/
        mergeSort.sort(ret.getKey().getListaDados());
        return ret.getKey().getListaDados();
    }

    //                           MUDAR PRA PALAVRA INVÉS DE NODE
    private Node atualizarPalavra(Node node) {  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (Pagina pagina : paginas){ 
            if(pagina.isModified() || node.getKey().getVezesBuscada() == 0){
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

                            if (cmp.equalsIgnoreCase(node.getKey().getChave())) {
                                cont++;
                            }

                            //System.out.print(cmp+" ");
                        }
                        //System.out.println();
                    }
                    Dados data = new Dados();
                    data.setTitulo(pagina.getTitulo());
                    int pos = node.getKey().getListaDados().indexOf(data);
                    if (cont != 0) { // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                        if(pos != -1){
                            Dados aux = node.getKey().getListaDados().get(pos);
                            aux.setQuantidade(cont);
                        }else{
                            ArrayList<Dados> a = node.getKey().getListaDados();
                            data.setTitulo(arquivo.getName());
                            data.setQuantidade(cont);
                            a.add(data);
                        }
                    }else{                                              // SE O CONTADOR FOR 0 E ESSE NÓ TIVER ESSA ESSA PAGINA EM SEUS DADOS, QUER DIZER QUE ANTES TINHA OCORRENCIAS, MAS DEIXOU DE TER. DEVE REMVER ENTÃO ESSA PÁGINA DA LISTA DE DADOS DESSE NÓ.
                        if(pos != -1){
                           ArrayList<Dados> a = node.getKey().getListaDados();
                           a.remove(pos);
                        }
                    }
                    input.close();
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                }
                
            }   
            
        }
        return node;
    }

    private void atualizarArvore(ArrayList<String> removidas) {
        ArrayList<Palavra> nosARemover = new ArrayList<>();
        // percorrer a lista de paginas verificando os isModified.

        // percorrer árvore procurando nós que tenham dados de páginas que foram removidas.
        Iterator<Node> itr = tree.iterator();
        
        while (itr.hasNext()) { //Percorre as Palavras da árvore tirando da lista de páginas delas as páginas removidas do repositório. 
            Node n = itr.next();
            ArrayList<Dados> dadosPalavra = n.getKey().getListaDados();
            for (int i=0; i < removidas.size();i++) {
                Dados data = new Dados();
                data.setTitulo(removidas.get(i));
                int aux = dadosPalavra.indexOf(data);
                if(aux != -1){                          // SE FOR ENCONTRADO O TITULO
                    dadosPalavra.remove(aux);
                }
            } 
            
            n = atualizarPalavra(n); // Depois de remover, ele atualiza os dados do nó.
   
            if(n.getKey().getListaDados().isEmpty()){
                nosARemover.add(n.getKey());
            }
        }
        for(Palavra p: nosARemover){
            tree.remover(p);
        }
    }

    private void verificarIntegridade(ArrayList<Pagina> pages) {
        boolean flag = true;

        ArrayList<String> removidas = new ArrayList<>();

        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < pages.size(); i++) {
            File arq = getPagina(pages.get(i).getTitulo());  
            if (arq == null) {
                flag = false;
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(pages.get(i).getTitulo());
            } else {                                             // Pega os arquivos não removidos
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
                if (pages.get(i).getInfo() != arq.lastModified()) {
                    flag = false;
                    pages.get(i).setModified(true);
                }
            }
        }
        for (String pag: removidas) {
            Pagina p = new Pagina();
            p.setTitulo(pag);
            pages.remove(p); 
        }
        
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE PAGINAS.*/
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());
        
        mergeSort.sort(pages);
        mergeSort.sort(aComparar);
        
        if(pages.size() < aComparar.size()){
            
            for (int i = 0; i < aComparar.size(); i++) {
                Pagina p = new Pagina();
                p.setTitulo(aComparar.get(i).getTitulo());
                if(!pages.contains(p)){
                    flag = false;
                    p.setTitulo(aComparar.get(i).getTitulo());
                    p.setInfo(aComparar.get(i).getInfo());
                    p.setModified(true);
                    pages.add(p);
                }
            }
        }
        
        if(!flag){
            atualizarArvore(removidas);
        }
    }
    
    public void verificarIntegridadeResultados(ArrayList a){ // AQUI É UMA LISTA DE DADOS. VAI TER QUE CONVERTER PRA PÁGINAS ANTES DE MANDAR PRA VERIFICAR INTEGRIDADE
        
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
    
    private void inverter(ArrayList<Dados> lista) { // REVER ISSOOOO...
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
        mergeSort.sort(lista, comp);
    }
    
    // REVER E TESTAR ISSO.
    public ArrayList topKMaisPalavra(int qtd){  // Fazer pra palavra e pra página // FAZER EXCEÇÃO DE QUE SE O USUÁRIO QUISER UM TOP (TAMANHO) MAIOR DO QUE O NÚMERO DE ELEMENTOS.
        
        return (ArrayList) infoPalavras.subList(0, qtd);  
    }
    // REVER E TESTAR ISSO.
    public ArrayList topKMenosPalavra(int qtd){// Fazer pra palavra e pra página
        
        return (ArrayList) infoPalavras.subList(infoPalavras.size(), qtd);
    }
    
    public ArrayList listarPaginas() {
        return paginas;
    }
    // REVER E TESTAR ISSO.
    private void atualizarTopKPalavras(Node node) {
        Dados p = new Dados();
        p.setTitulo(node.getKey().getChave());
        p.setQuantidade(node.getKey().getVezesBuscada());
        infoPalavras.add(p);
        mergeSort.sort(infoPalavras);
    }

}
