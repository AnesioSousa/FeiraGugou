package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import model.*;
import util.*;
import util.ArvoreAVL.Node;

/**
 *
 * @author Anésio
 */
public class Controlador {
    private ArrayList<Pagina> paginas = new ArrayList<>();
    private Arquivos man = new Arquivos();
    private ArvoreAVL tree = new ArvoreAVL();
    
    // <<<<<<<<<<<<<<<<CODIGO MUITO PARECIDO DE atribuirPaginas E getArquivo, RESOLVER ISSO !!!!>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<ONDE IREI CHAMAR ESSE MÉTODO? ELE PRECISA SER INICIADO LOGO QUE A CLASSE É INICIADA >>>>>>>>>>
    //                                               ^
    // SOLUÇÃO:
    public Controlador(){
        File[] arquivos = man.obter();
        for (File arquivo : arquivos) {
            Pagina pag = new Pagina();
            int pos = arquivo.getName().indexOf(".");
            pag.setTitulo(arquivo.getName().substring(0, pos)); 
            paginas.add(pag);
        }
    }
    // <<<<<<<<<<<<<< COM CERTEZA VERIFICAR ESTA MERDA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
    public Iterator pesquisar(String palavra){
        // Se não estiver na árvore;
        if(pesquisarEmArvore(palavra) == false){ // !pesquisarEmArvore(palavra)
            Palavra nova = new Palavra(palavra);
            pesquisarEmArquivos(nova);
            tree.inserir(nova.getPalavraChave());
        }
        Node ret = tree.encontrar(palavra);
        return ret.getDado().iteradorDados();
        // return o iterador (ou alguma outra forma de percorrer) da lista de titulos pertencente ao objeto Palavra que contém essa palavra chave pesquisada.
    }
    
    private boolean pesquisarEmArvore(String palavra){
        Node ret = tree.encontrar(palavra);
        
        if(ret != null)
            return true;
        
        return false;
    }
     
    // acha que fica bom mudar o nome para "atualizarPalavra" ou algo assim?
    private void pesquisarEmArquivos(Palavra palavra){  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        int cont = 0;
        for (Pagina pagina : paginas) {
            File arquivo = getArquivo(pagina.getTitulo());
            try{
                Scanner input = new Scanner(arquivo);
                while(input.hasNext()){
                    String linha = input.nextLine();
                    
                    StringTokenizer tkn = new StringTokenizer(linha);
                    
                    while(tkn.hasMoreTokens()){
                        String cmp = tkn.nextToken();
                        
                        if(cmp.equalsIgnoreCase(palavra.getPalavraChave())){
                            cont++;
                        }

                        //System.out.print(cmp+" ");
                    }
                    //System.out.println();
                }
                palavra.adicionarPagina(pagina.getTitulo(), cont);
                System.out.println(cont);
                input.close();
            }catch(FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
    }
    
    // ESSE MÉTODO FUTURAMENTE VAI SER MAIS ÚTIL DO QUE O MÉTODO "atribuirPaginas"
    public File getArquivo(String titulo){
        File[] arquivos = man.obter();
        for(File arquivo: arquivos){
            int pos = arquivo.getName().indexOf(".");
            String aux = arquivo.getName().substring(0, pos);
            
            if(aux.equals(titulo)){
                return arquivo;
            }
        }
        return null;
    }
    
}
