package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    private Arquivos man = new Arquivos();
    public ArvoreAVL tree = new ArvoreAVL();
    private File[] arquivos = man.obter();
    
    // SE A PALAVRA NÃO ESTIVER NA ÁRVORE, ELE VAI INSERIR, ATUALIZAR OS DADOS E RETORNAR O ITERADOR. SE JÁ ESTIVER, ELE SIMPLESMENTE RETORNA O ITERADOR;
    public Iterator pesquisar(String palavra){ 
        /* TODA VEZ ANTES DE PESQUISAR, SERÁ NECESSÁRIO VERIFICAR A INTEGRIDADE DOS ARQUIVOS. SE ELES SOFRERAM ALTERAÇÕES, ELES DEVERÃO SER RE-LIDOS, E OS NÓS ATUALIZADOS.
        if(verificarIntegridade() == false){ // testar -> !verificarIntegridade()
            
        }*/
        
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
        for (File arquivo: arquivos) { 
            int cont = 0;
            try{
                Scanner input = new Scanner(arquivo);
                while(input.hasNext()){
                    String linha = input.nextLine();
                    
                    String aux = prepararLinha(linha);
                    
                    StringTokenizer tkn = new StringTokenizer(aux);
                    
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
                    pag.setTitulo(arquivo.getName());
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
    
    private String prepararLinha(String str2Clean){
        Pattern padrao = Pattern.compile("[\\p{L}0-9]+{1,}"); // ver se dá pra retirar o {1,}
        Matcher combinador = padrao.matcher(str2Clean);
        String palavrasPreparadas;
        String a = "";
        
        while(combinador.find()){
            if(combinador.group().length() != 0){
                palavrasPreparadas = combinador.group();
                a += palavrasPreparadas+" ";
            }
        }
        
        return a;
    }  
    
    private boolean verificarMultiPalavras(String palavra){
        Pattern padrao = Pattern.compile("\\s+[A-Za-z]+");
        Matcher combinador = padrao.matcher(palavra);

        return combinador.find();
    }
}
