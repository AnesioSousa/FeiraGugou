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
 * @author Anésio
 */
public class Controlador {

    private Arquivos files = new Arquivos();
    private MergeSort mergeSort = new MergeSort();
    public ArvoreAVL tree = new ArvoreAVL();
    private ArrayList<File> arquivos = files.obter();
    private ArrayList<Pagina> paginas = new ArrayList<>();      // Verificar se isso é realmente necessário.

    public Controlador() {
        obterPaginas(paginas, arquivos);
    }

    public Iterator pesquisar(String palavra) {
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        boolean teste = verificarIntegridade();
        System.out.println(teste);
        Node ret = tree.encontrar(palavra);        // Ele já entra aqui com o repositório atualizado.

        if (ret == null) {
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
        }
        ret.incrementVezesBuscada();
        mergeSort.sort(ret.getPaginas());

        return ret.listarPaginas();
    }

    private Node atualizarPalavra(Node node) {  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (File arquivo : arquivos) {
            int cont = 0;
            try {
                Scanner input = new Scanner(arquivo);
                while (input.hasNext()) {
                    String linha = input.nextLine();

                    String aux = prepararLinha(linha);

                    StringTokenizer tkn = new StringTokenizer(aux);

                    while (tkn.hasMoreTokens()) {
                        String cmp = tkn.nextToken();

                        if (cmp.equalsIgnoreCase(node.getDado())) {
                            cont++;
                        }

                        //System.out.print(cmp+" ");
                    }
                    //System.out.println();
                }
                if (cont != 0) { // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                    ArrayList<Pagina> a = node.getPaginas();
                    Pagina pag = new Pagina();
                    pag.setTitulo(arquivo.getName());
                    pag.setOcorrencias(cont);
                    a.add(pag);
                }
                input.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
        return node;
    }

    private void obterPaginas(ArrayList<Pagina> lista, ArrayList<File> arq) {
        for (File a : arq) {
            Pagina pagina = new Pagina();
            pagina.setTitulo(a.getName());
            pagina.setInfo(a.lastModified());
            lista.add(pagina);
        }
    }

    private boolean verificarIntegridade() {
        ArrayList<File> repoAtual = files.obter();
        boolean flag = true;
        
        if(arquivos.size() != repoAtual.size()){
            if(arquivos.size() < repoAtual.size()){ // (FOI ADD) Se tiver menos itens no local, do que no repositório.
                System.out.println("Itens foram adicionados!!");
                atualizarArquivos();
            
            }else{                                  // (FOI RMV) Se tiver mais itens no local do que no repositório.
                System.out.println("Itens foram removidos!!");
                atualizarArquivos();
            }
                
            flag = false;
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

    public File getArquivo(String titulo) {
        ArrayList<File> arq = files.obter();
        for (File arquivo : arq) {
            int pos = arquivo.getName().indexOf(".");
            String aux = arquivo.getName().substring(0, pos);

            if (aux.equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    private void atualizarArquivos() {
        arquivos = files.obter();
    }

    public Iterator getPaginas() {
        return paginas.iterator();
    }

}
