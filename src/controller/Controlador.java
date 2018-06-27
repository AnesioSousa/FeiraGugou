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
    public ArvoreAVL tree;   // LEMBRAR DE DEIXAR PRIVATE!!!!!!!!!!!!!!!!!!!!!
    private ArrayList<Pagina> paginas;
    private Node atual;

    public Controlador() {
        this.files = new Arquivos();
        this.mergeSort = new MergeSort();
        this.tree = new ArvoreAVL();
        this.paginas = new ArrayList<>(); 
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio());
    }

    public Iterator pesquisar(String palavra) {
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        verificarIntegridade();
        
        Node ret = tree.encontrar(palavra);        // Ele já entra aqui com o repositório atualizado.

        if (ret == null) {
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
            if (ret.getListaDados().isEmpty()) {
                tree.remover(palavra);
                return null;
            }
        }
        ret.incrementVezesBuscada();
        atual = ret;
        mergeSort.sort(ret.getListaDados());
        
        
        return ret.listarDados();
    }

    private Node atualizarPalavra(Node node) {  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (Pagina pagina : paginas){ 
            if(pagina.isModified() || node.getVezesBuscada() == 0){
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

                            if (cmp.equalsIgnoreCase(node.getChave())) {
                                cont++;
                            }

                            //System.out.print(cmp+" ");
                        }
                        //System.out.println();
                    }
                    Dados data = new Dados();
                    data.setTitulo(pagina.getTitulo());
                    int pos = node.getListaDados().indexOf(data);
                    if (cont != 0) { // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                        if(pos != -1){
                            Dados aux = node.getListaDados().get(pos);
                            aux.setFrequencia(cont);
                        }else{
                            ArrayList<Dados> a = node.getListaDados();
                            data.setTitulo(arquivo.getName());
                            data.setFrequencia(cont);
                            a.add(data);
                        }
                    }else{                                              // SE O CONTADOR FOR 0 E ESSE NÓ TIVER ESSA ESSA PAGINA EM SEUS DADOS, QUER DIZER QUE ANTES TINHA OCORRENCIAS, MAS DEIXOU DE TER. DEVE REMVER ENTÃO ESSA PÁGINA DA LISTA DE DADOS DESSE NÓ.
                        if(pos != -1){
                           ArrayList<Dados> a = node.getListaDados();
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
        ArrayList<String> nosARemover = new ArrayList<>();
        // percorrer a lista de paginas verificando os isModified.

        // percorrer árvore procurando nós que tenham dados de páginas que foram removidas.
        Iterator<Node> itr = tree.iterator();
        
        while (itr.hasNext()) {                                           
            Node n = itr.next();
            ArrayList<Dados> dadosPalavra = n.getListaDados();
            for (int i=0; i < removidas.size();i++) {
                Dados data = new Dados();
                data.setTitulo(removidas.get(i));
                int aux = dadosPalavra.indexOf(data);
                if(aux != -1){                          // SE FOR ENCONTRADO O TITULO
                    dadosPalavra.remove(aux);
                }
            } 
            n = atualizarPalavra(n);
   
            if(n.getListaDados().isEmpty()){
                nosARemover.add(n.getChave());
            }
        }
        for(String p: nosARemover){
            tree.remover(p);
        }
    }

    private void verificarIntegridade() {
        boolean flag = true;

        ArrayList<String> removidas = new ArrayList<>();

        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getPagina(paginas.get(i).getTitulo());  
            if (arq == null) {
                flag = false;
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i).getTitulo());
            } else {                                             // Pega os arquivos não removidos
                //System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
                if (paginas.get(i).getInfo() != arq.lastModified()) {
                    flag = false;
                    paginas.get(i).setModified(true);
                }
            }
        }
        for (String pag: removidas) {
            Pagina p = new Pagina();
            p.setTitulo(pag);
            paginas.remove(p); 
        }
        
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE PAGINAS.*/
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());
        
        mergeSort.sort(paginas);
        mergeSort.sort(aComparar);
        
        if(paginas.size() < aComparar.size()){
            
            for (int i = 0; i < aComparar.size(); i++) {
                Pagina p = new Pagina();
                p.setTitulo(aComparar.get(i).getTitulo());
                if(!paginas.contains(p)){
                    flag = false;
                    p.setTitulo(aComparar.get(i).getTitulo());
                    p.setInfo(aComparar.get(i).getInfo());
                    p.setModified(true);
                    paginas.add(p);
                }
            }
        }
        
        if(!flag){
            atualizarArvore(removidas);
        }
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
    
    public void listarResultadosEmOrdemCrescente() { // REVER ISSOOOO...
        if (atual != null) {
            Comparator<Dados> comp = new Comparator<Dados>() {
                @Override
                public int compare(Dados o1, Dados o2) {
                    if (o1.getFrequencia() > o2.getFrequencia()) {
                        return 1;
                    }
                    if (o1.getFrequencia() < o2.getFrequencia()) {
                        return -1;
                    }
                    return 0;
                }

            };
            mergeSort.sort(atual.getListaDados(), comp);
        }
    }
    
    public void listarResultadosEmOrdemDecrescente(){ // REVER ISSOOOO...
        if (atual != null) {
            mergeSort.sort(atual.getListaDados());
        }
        
    }

    public Iterator topKMais(){  // Fazer pra palavra e pra página
        return null;
    }
    
    public Iterator topKMenos(){// Fazer pra palavra e pra página
        return null;
    }
    
    public Iterator listarPaginas() {
        return paginas.iterator();
    }

}
