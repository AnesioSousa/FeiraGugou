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
    private ArrayList<Pagina> paginas = new ArrayList<>();
    
    public Controlador(){
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio()); 
    }

    public Iterator pesquisar(String palavra) {
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        //boolean teste = verificarIntegridade();
        //System.out.println(teste);
        Node ret = tree.encontrar(palavra);        // Ele já entra aqui com o repositório atualizado.

        if (ret == null) {
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
        }
        ret.incrementVezesBuscada();
        mergeSort.sort(ret.getDados());
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<< SE A LISTA DE PAGINAS DE UMA PALAVRA ESTIVER VAZIA QUER DIZER QUE NÃO ACHOU MATCHES, ENTÃO DEVE REMOVER O NÓ >>>>>>>>>>>>>>>>>>
        return ret.listarPaginas();
    }

    private Node atualizarPalavra(Node node) {  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (Pagina pagina: paginas) {
            int cont = 0;
            File arquivo = getArquivo(pagina.getTitulo());
            
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
                if (cont != 0) { // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                    ArrayList<Dados> a = node.getDados();
                    Dados data = new Dados();
                    data.setTitulo(arquivo.getName());
                    data.setFrequencia(cont);
                    a.add(data);
                }
                input.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
        return node;
    }

    private void atualizarPaginas(){
        
    }
    
    /*private void atualizarArquivos() {
        arquivos = files.obterRepositorio();
    }*/

    /*private boolean verificarIntegridade() {       //  << AGORA TEM QUE VER A QUESTÃO: SE PAGINAS FORAM ADICIONADAS OU REMOVIDAS,
        ArrayList<File> repoAtual = files.obterRepositorio(); // QUAIS FORAM ESSAS PÁGINAS? JUSTAMENTE PRA PODER ATUALIZAR A LISTA DE PÁGINAS DO CONTROLLER
        boolean flag = true;                       // E OS NÓS (PALAVRAS BUSCADAS) QUE TEM RESULTADOS NESSES ARQUIVOS QUE FORAM ALTERADOS. 
        
        // VEI, VAI TER QUE PERCORRER TUDO DE QUALQUER JEITO. VÁ POR MIM. EX: UM ARQUIVO FOI REMOVIDO, E OUTROS SÓ ALTERADOS. O QUE FAZER? SÓ IDENTIFICAR E ATUALIZAR A ÁRVORE POR CAUSA DO REMOVIDO?
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
    }*/

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
        ArrayList<File> arq = files.obterRepositorio();
        for (File arquivo : arq) {
            if (arquivo.getName().equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    public Iterator getPaginas() {
        return paginas.iterator();
    }

}
