package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import model.Pagina;

/**
 * Classe que gera objetos que ajudam na manipulação de arquivos.
 * @author Anésio Sousa
 */
public class Arquivos{

    /**
     * Retorna a os arquivos do repositório em forma de ArrayList.
     * @return lista de arquivos.
     */
    public ArrayList<File> obterRepositorio(){
        ArrayList<File> arq = new ArrayList<>();
        try{
            File[] arquivos;
            String dirPadrao = new File ("repo").getCanonicalPath();
            File diretorio = new File(dirPadrao); 
            arquivos = diretorio.listFiles();
            arq.addAll(Arrays.asList(arquivos));
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return arq;
    }
    
    /**
     * Recebe uma lista vazia de páginas e uma lista de Files com elementos, e copia dados dos Files para objetos Pagina inserindo na lista vazia. 
     * @param lista lista vazia de páginas.
     * @param arq lista com elementos de arquivos.
     * @return lista cheia de páginas com dados retirados da lista de arquivos.
     */
    public ArrayList<Pagina> passarArqParaPaginas(ArrayList<Pagina> lista, ArrayList<File> arq){
        for (File a : arq) {
            Pagina pagina = new Pagina();
            pagina.setTitulo(a.getName());
            pagina.setInfo(a.lastModified());
            lista.add(pagina);
        }
        
        return lista;
    }

}
