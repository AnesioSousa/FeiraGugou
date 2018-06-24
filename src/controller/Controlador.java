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
        verificarIntegridade();

        Node ret = tree.encontrar(palavra);        // Ele já entra aqui com o repositório atualizado.

        if(ret == null){
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
        }
        if(ret.getDados().isEmpty()){
           tree.remover(palavra);
           return null;
        }
        ret.incrementVezesBuscada();
        mergeSort.sort(ret.getDados());
        return ret.listarDados();
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

    private void atualizarArvore(ArrayList<String> removidas){
        // percorrer a lista de paginas verificando os isModified.
        // percorrer árvore procurando nós que tenham dados de páginas que foram removidas.
    }
    
    private boolean verificarIntegridade() {       
        boolean flag = true;                                             
                             
        //  << AGORA TEM QUE VER A QUESTÃO: SE PAGINAS FORAM ADICIONADAS OU REMOVIDAS,
        // QUAIS FORAM ESSAS PÁGINAS? JUSTAMENTE PRA PODER ATUALIZAR A LISTA DE PÁGINAS DO CONTROLLER
        // E OS NÓS (PALAVRAS BUSCADAS) QUE TEM RESULTADOS NESSES ARQUIVOS QUE FORAM ALTERADOS. 
        
        ArrayList<String> removidas = new ArrayList<>();
        
        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getArquivo(paginas.get(i).getTitulo()); // Verifica e tira da lista os arquivos removidos do diretório.  // <<<<<< SE "aComparar" JA TEM TODOS OS ARQUIVOS, POR QUE NÃO TO USANDO "aComparar" PRA FAZER OS TESTES?
            if(arq == null){
                System.out.println(paginas.get(i).getTitulo() +" "+ "FOI REMOVIDO!!");
                removidas.add(paginas.get(i).getTitulo());
                paginas.remove(i);
            }else{                                             // Pega os arquivos não removidos
                System.out.println("ARQUIVO EXISTEEE");
                if(paginas.get(i).getInfo() == arq.lastModified()){
                    // Pula pro próximo.
                }else{
                    flag = false;
                    paginas.get(i).setIsModified(true); // Verifica quais páginas foram modificadas, e as marca. Para depois ser feita
                                                        //a releitura desses arquivos, e a atualização dos nós que tem essas paginas modificadas.
                }
            }        
        }
        // TEM QUE ADD NOVOS ARQUIVOS À LISTA DE PAGINAS:
        int novo = 0;
        ArrayList<Pagina> aComparar = new ArrayList();
        aComparar = files.passarArqParaPaginas(aComparar, files.obterRepositorio());
        
        if(paginas.size() < aComparar.size()){
                                
            for (Pagina atual: aComparar) {
                
                for (Pagina pag : paginas) {
                    if(!atual.getTitulo().equals(pag.getTitulo())){
                        novo++;
                    }
                }
                if(novo == paginas.size()){
                    Pagina p = new Pagina();
                    p.setTitulo(atual.getTitulo());
                    p.setInfo(atual.getInfo());
                    paginas.add(p);                           // Ver se não da pra deixar só paginas.add(atual);
                }
            }
        }
        
        // AQUI TEM QUE ATUALIZAR A ÁRVORE UTILIZANDO A NOVA LISTA DE PAGINAS.

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
