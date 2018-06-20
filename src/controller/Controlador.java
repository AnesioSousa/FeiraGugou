package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import model.*;
import util.*;

/**
 *
 * @author Anésio
 */
public class Controlador {
    private ArrayList<String> nomesArquivos = new ArrayList<>();    // Já esse atributo é somente utilizado para salvar os nomes dos arquivos, pq não botar uma lista de strings?
    private Arquivos man = new Arquivos();
    public ArvoreAVL tree = new ArvoreAVL();
    
    // <<<<<<<<<<<<<<<<CODIGO MUITO PARECIDO DE atribuirPaginas E getArquivo, RESOLVER ISSO !!!!>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<ONDE IREI CHAMAR ESSE MÉTODO? ELE PRECISA SER INICIADO LOGO QUE A CLASSE É INICIADA >>>>>>>>>>
    //                                               ^
    // SOLUÇÃO:
    public Controlador(){
        File[] arquivos = man.obter();
        for (File arquivo : arquivos) {
            int pos = arquivo.getName().indexOf(".");
            String nome = arquivo.getName().substring(0, pos); 
            nomesArquivos.add(nome);
        }
    }
    // SE A PALAVRA NÃO ESTIVER NA ÁRVORE, ELE VAI INSERIR, ATUALIZAR OS DADOS E RETORNAR O ITERADOR. SE JÁ ESTIVER, ELE SIMPLESMENTE RETORNA O ITERADOR;
    public Iterator pesquisar(String palavra){
        Node ret = tree.encontrar(palavra);
        
        if(ret == null){ 
            tree.inserir(palavra);
            ret = tree.encontrar(palavra);  // tentar tirar isso depois
            ret = atualizarDados(ret);
        }
        ret.incrementVezesBuscada();

        return ret.listarPaginas();
    }
     
    private Node atualizarDados(Node node){  // SÓ VAI ACESSAR AQUI SE A PALAVRA CHAVE NÃO ESTIVER NA ÁRVORE;
        for (String titulo: nomesArquivos) { 
            int cont = 0;
            File arquivo = getArquivo(titulo);  
            try{
                Scanner input = new Scanner(arquivo);
                while(input.hasNext()){
                    String linha = input.nextLine();
                    
                    StringTokenizer tkn = new StringTokenizer(linha);
                    
                    while(tkn.hasMoreTokens()){
                        String cmp = tkn.nextToken();
                        
                        if(cmp.equalsIgnoreCase(node.getDado())){
                            cont++;
                        }

                        //System.out.print(cmp+" ");
                    }
                    //System.out.println();
                }
                if(cont != 0){ // Se tiver pelo menos 1 ocorrência da palavra no arquivo txt
                    ArrayList<Pagina> a = node.getPaginas();
                    Pagina pag = new Pagina();
                    pag.setTitulo(titulo);
                    pag.setOcorrencias(cont);
                    a.add(pag);
                }
                input.close();
            }catch(FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
        return node;
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
