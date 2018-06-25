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

    public Controlador() {
        this.paginas = files.passarArqParaPaginas(paginas, files.obterRepositorio());
    }

    public Iterator pesquisar(String palavra) {
        ///TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        if (!verificarIntegridade()) {

        }

        Node ret = tree.encontrar(palavra);        // Ele já entra aqui com o repositório atualizado.

        if (ret == null) {
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarPalavra(ret);
        }
        if (ret.getDados().isEmpty()) {
            tree.remover(palavra);
            return null;
        }
        ret.incrementVezesBuscada();
        mergeSort.sort(ret.getDados());
        return ret.listarDados();
    }

    private Node atualizarPalavra(Node node) {  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (Pagina pagina : paginas) {
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

    private void atualizarNode(File arq, Node node) { // TENTAR DEIXAR "atualizarPalavra" E "atualizarNode" UM SÓ, JÁ QUE FAZEM QUASE A MESMA COISA.
        int cont = 0;
        try {
            Scanner input = new Scanner(arq);
            while (input.hasNext()) {
                String linha = input.nextLine();
                String aux = prepararLinha(linha);
                StringTokenizer tkn = new StringTokenizer(aux);
                while (tkn.hasMoreTokens()) {
                    String cmp = tkn.nextToken();
                    if (cmp.equalsIgnoreCase(node.getChave())) {
                        cont++;
                    }
                }
            }
            if (cont != 0) {
                ArrayList<Dados> a = node.getDados();
                Dados data = new Dados();
                data.setTitulo(arq.getName());
                data.setFrequencia(cont);
                a.add(data);
            }
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    private void atualizarArvore(ArrayList<String> removidas, ArrayList<String> modificadas) {
        // percorrer a lista de paginas verificando os isModified.

        // percorrer árvore procurando nós que tenham dados de páginas que foram removidas.
        Iterator<Node> itr = tree.iterator();
        
        while (itr.hasNext()) {                                           
            Node n = itr.next();
            ArrayList<Dados> d = n.getDados();
            for (int i=0; i < removidas.size();i++) {
                Dados data = new Dados();
                data.setTitulo(removidas.get(i));
                int aux = d.indexOf(data);
                if(aux != -1){                          // SE FOR ENCONTRADO O TITULO
                    d.remove(aux);
                }
            }   
            /* ATUALIZAR DADOS DOS NODES COM AS PÁGINAS MODIFICADAS.*/
            /* ATUALIZAR DADOS DOS NODES COM AS PÁGINAS ADICIONADAS.*/
        }
    }

    private boolean verificarIntegridade() {
        boolean flag = true;

        ArrayList<String> removidas = new ArrayList<>();
        ArrayList<String> modificadas = new ArrayList<>();

        // SE PASSAR DIRETO, É PQ NENHUM ARQUIVO FOI REMOVIDO NEM MODIFICADO.
        for (int i = 0; i < paginas.size(); i++) {
            File arq = getPagina(paginas.get(i).getTitulo()); // Verifica e tira da lista os arquivos removidos do diretório.  // <<<<<< SE "aComparar" JA TEM TODOS OS ARQUIVOS, POR QUE NÃO TO USANDO "aComparar" PRA FAZER OS TESTES?
            if (arq == null) {
                flag = false;
                System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO REMOVIDO!!");
                removidas.add(paginas.get(i).getTitulo());
                paginas.remove(i);
            } else {                                             // Pega os arquivos não removidos
                System.out.println(paginas.get(i).getTitulo() + " " + "ARQUIVO PRESENTE!!");
                if (paginas.get(i).getInfo() != arq.lastModified()) {
                    flag = false;
                    modificadas.add(paginas.get(i).getTitulo());
                }
            }
        }
        /* TEM QUE ADD NOVOS ARQUIVOS À LISTA DE PAGINAS.*/
        
        if(!flag){
            atualizarArvore(removidas, modificadas);
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

    public File getPagina(String titulo) {
        ArrayList<File> arq = files.obterRepositorio();
        for (File arquivo : arq) {
            if (arquivo.getName().equals(titulo)) {
                return arquivo;
            }
        }
        return null;
    }

    public Iterator listarPaginas() {
        return paginas.iterator();
    }

}
